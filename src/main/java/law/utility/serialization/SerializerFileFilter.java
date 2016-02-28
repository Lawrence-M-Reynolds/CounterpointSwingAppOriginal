package law.utility.serialization;

import java.util.List;
import java.io.File;

import javax.swing.filechooser.FileFilter;
import java.util.Arrays;

public class SerializerFileFilter extends FileFilter{
	
	private List<String> extensions;
	
	public SerializerFileFilter(String[] extensions){
		this.extensions = Arrays.asList(extensions);
	}
	@Override
	public boolean accept(File file) {
	    if (file.isDirectory()) {
	        return true;
	    }

	    String extension = Utils.getExtension(file);
	    if (extension != null) {
	    	if(extensions.contains(extension)){
	        	return true;
	        }else {
	            return false;
	        }
	    }
	    return false;
	}
	@Override
	public String getDescription() {
		return extensions.toString();
	}
	
	private static class Utils {

	    /*
	     * Get the extension of a file.
	     */  
	    public static String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i).toLowerCase();
	        }
	        return ext;
	    }
	}

}
