package law.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	private Properties appProperties;
	private File appPropertiesFile;
	
	public PropertiesManager(){
		appPropertiesFile = new File("CompAppPropertiesfile");
		FileInputStream fis;
		try {
			if(!appPropertiesFile.exists())
				appPropertiesFile.createNewFile();
			fis = new FileInputStream(appPropertiesFile);
			appProperties = new Properties();
			appProperties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("Properties file not found");
		} catch (IOException e) {
			System.out.println("IOException with properties file loading");
		}
	}
	
	public Properties getAppProperties(){
		return appProperties;
	}
	
	public void saveAllProperties(){
		try {
			FileOutputStream fos = new FileOutputStream(appPropertiesFile);
			appProperties.store(fos, "General properties file for CompApp application.");
		} catch (FileNotFoundException e) {
			// GENERATED_TAG Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// GENERATED_TAG Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
