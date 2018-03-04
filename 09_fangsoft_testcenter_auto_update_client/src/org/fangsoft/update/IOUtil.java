package org.fangsoft.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {
	public static void closeIO(OutputStream os) {
		try {
			if (os != null)
				os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void closeIO(InputStream is) {
		try {
			if (is != null)
				is.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void closeIO(Reader reader) {
		try {
			if (reader != null)
				reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void closeIO(Writer writer) {
		try {
			if (writer != null)
				writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean createFilePath(String fileName) {
		File file = new File(fileName);
		return file.getParentFile().mkdirs();
	}

	public static void saveAsFile(String content, String fileName) {
		createFilePath(fileName);
		BufferedWriter bw = null;
		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(
					fileName), "UTF-8");
			bw = new BufferedWriter(writer);
			bw.write(content);
			bw.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtil.closeIO(bw);
		}
	}

	public static final int BUFFER_SIZE = 8192;

	public static void saveAsFile(byte[] content, String fileName) {
		createFilePath(fileName);
		BufferedOutputStream bos = null;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos, BUFFER_SIZE);
			bos.write(content);
			bos.flush();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtil.closeIO(bos);
		}
	}

	public static String readFromTXTFile(String fileName) {
		BufferedReader br = null;
		StringBuffer buf = new StringBuffer(BUFFER_SIZE);
		try {
			Reader reader = new InputStreamReader(
					new FileInputStream(fileName), "UTF-8");
			br = new BufferedReader(reader);
			String text = null;
			while ((text = br.readLine()) != null) {
				buf.append(text);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtil.closeIO(br);
		}
		return buf.toString();
	}

	public static byte[] readFromBINFile(String fileName) {
		BufferedInputStream bis = null;
		byte[] readByte = new byte[0];
		try {
			FileInputStream fis = new FileInputStream(fileName);
			bis = new BufferedInputStream(fis, BUFFER_SIZE);
			byte[] input = new byte[BUFFER_SIZE];
			int reads = -1;
			while ((reads = bis.read(input)) != -1) {
				byte[] bytes = new byte[readByte.length + reads];
				System.arraycopy(readByte, 0, bytes, 0, readByte.length);
				System.arraycopy(input, 0, bytes, readByte.length, reads);
				readByte = bytes;
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtil.closeIO(bis);
		}
		return readByte;
	}

	public static void copyFile(String srcFileName, String destFileName) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(srcFileName));
			bos = new BufferedOutputStream(new FileOutputStream(destFileName));
			byte[] buffer = new byte[BUFFER_SIZE];
			int reads = 0;
			while ((reads = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, reads);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtil.closeIO(bis);
			IOUtil.closeIO(bos);
		}
		
	}
}
