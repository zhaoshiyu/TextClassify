package cn.edu.kmust.nlp.classifier;

/**
 * 
 * @author  Zhao Shiyu
 *
 *单词的统计信息包括单词的个数和词频
 *
 */
public class WordItem {
	
	double count;//单词的个数
	double frequency;//词频，它需要在得出文档中词的大小之后才能计算
	public WordItem(double count) {
		this.count=count;
		this.frequency=-1;
	}
	public void setFrequency(double frequency){
		this.frequency=frequency;
	}

}
