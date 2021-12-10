package tr.com.jalgo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import tr.com.jalgo.model.exceptions.FileOperationException;

public class FileUtils {

	public static void save(String destinationPath, InputStream inputStream) {
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destinationPath)));
			int inByte;
			while ((inByte = bis.read()) != -1)
				bos.write(inByte);
			bis.close();
			bos.close();
		} catch (FileNotFoundException e) {
			throw new FileOperationException(e.getMessage());
		} catch (IOException e) {
			throw new FileOperationException(e.getMessage());
		}
	}
}
