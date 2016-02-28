package law.utility.serialization;

import java.awt.Component;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;

public class Serializer {
	
	private JFileChooser  fileChooser;
	private String saveExtension;
	
	public Serializer(String saveAndLoadLocation, String[] fileTypeExtensions, int saveExtensionIndex){
		fileChooser = new JFileChooser(saveAndLoadLocation);
		FileFilter fileFilter = new SerializerFileFilter(fileTypeExtensions);
		saveExtension = fileTypeExtensions[saveExtensionIndex];
		fileChooser.setFileFilter(fileFilter);
	}
	
	public File getLastSaveOrLoadLocation(){
		return fileChooser.getCurrentDirectory();
	}
	
	public void saveObject(Component frame, Object o){
		if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();
			if(file.getName().indexOf(saveExtension) < 0)
				file = new File(file.getAbsolutePath() + saveExtension);
			try {
				FileOutputStream fileOutStream = new FileOutputStream(file);
				ObjectOutputStream outStream = new ObjectOutputStream(fileOutStream);
				outStream.writeObject(o);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Object loadObject(Component frame){
		if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
			try {
				FileInputStream fileInStream = new FileInputStream(fileChooser.getSelectedFile());
				ObjectInputStream inObjectStream = new ObjectInputStream(fileInStream);
				return inObjectStream.readObject();
			} catch (IOException e) {
				System.out.println("Could not load item - IOException.");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not load item - ClassNotFoundException.");
			}
		}
		return null;
	}

}