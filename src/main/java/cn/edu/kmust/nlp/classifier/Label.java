package cn.edu.kmust.nlp.classifier;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Zhao Shiyu
 *
 *         类别标签：体育、经济、文化等
 *
 */
public class Label {

	
	Map<String, WordItem> map = new HashMap<String, WordItem>(); // 用来存放每个单词及其统计信息
	double wordCount;// 某个类别标签下的所有单词个数
	double documentCount;// 某个类别标签下的所有文档个数

	public Label() {
		this.map = null;
		this.wordCount = -1;
		this.documentCount = -1;
	}

	public void set(Map<String, WordItem> map, double wordCount,
			double documentCount) {
		this.map = map;
		this.wordCount = wordCount;
		this.documentCount = documentCount;
	}

}
