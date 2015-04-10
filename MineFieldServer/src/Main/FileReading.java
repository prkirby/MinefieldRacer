package Main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

//reads a given file, specifically to read the maps
public class FileReading {
	
	public File[] getMaps(){
		File f = new File(FileReading.class.getResource("/MAPS").getFile());


		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt");
			}
		};
		File[] files = f.listFiles(textFilter);
		return files;
	}
}



