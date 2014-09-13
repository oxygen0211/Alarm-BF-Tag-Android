package com.engelhardt.BF.Alarm.App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PropertyManager {
	
	private static String filename = "BF/last.fl";
	private File propertyFile;

	public PropertyManager(File externalDirectory) {
		propertyFile = new File(externalDirectory,filename);
	}

	public void saveLasthost(String host) {
		FileWriter writer = null;
		try {
			if (!propertyFile.exists()) {
				propertyFile.mkdirs();
				propertyFile.createNewFile();
			}
			writer = new FileWriter(propertyFile);
			writer.write(host);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public String getLastHost() {
		if(propertyFile.exists())
		{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(propertyFile));
			return reader.readLine();

		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			return "";
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// ignore
			}
		}
		}
		else
		{
			return "";
		}
	}
}
