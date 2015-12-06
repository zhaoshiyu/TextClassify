package cn.edu.kmust.nlp.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

/**
 * 中文分词
 * @author  Zhao Shiyu
 *
 */
public class Seg {
	
	/**
	 * 使 用省内存的方式读取文件并完成分词
	 * 
	 * @param path
	 * @return
	 */
	public static Vector<String> token(String path) {
		Vector<String> vec = new Vector<String>();
		String line = "";
		try {
			BufferedReader bw = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "GBK"));
			while ((line = bw.readLine()) != null) {
				//List<Term> terms = NlpAnalysis.parse(str);
				List<Term> terms = BaseAnalysis.parse(line);
				for (Term term : terms) {
					vec.add(term.getName());
				}
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vec;
	}
	

}
