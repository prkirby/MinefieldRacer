package Main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileReading {
	
	public File[] getMaps(){
		File f = new File("MAPS");

		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".txt");
			}
		};
		File[] files = f.listFiles(textFilter);
		for (File file : files) {
			System.out.println(file);
		}
		return files;
	}
}



