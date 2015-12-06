package cn.edu.kmust.nlp.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * 
 * @author  Zhao Shiyu
 *
 */
public class IOUtil {
	
	/**
	 *使 用省内存的方式读取文件
	 * @param path
	 * @return 
	 */
	public static LinkedList<String> readLinesList(String path) {
		LinkedList<String> result = new LinkedList<String>();
		String line = null;
		try {
			BufferedReader bw = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "UTF-8"));
			while ((line = bw.readLine()) != null) {
				result.add(line);
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
