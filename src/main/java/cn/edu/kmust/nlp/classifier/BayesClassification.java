package cn.edu.kmust.nlp.classifier;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import cn.edu.kmust.nlp.gui.BayesClassificationGUI;
import cn.edu.kmust.nlp.util.Seg;

/**
 * 贝叶斯分类器
 * 
 * @author Zhao Shiyu
 *
 */
public class BayesClassification {
	private String label = null;
	private long trainTime = 0;
	public String[] labelsName = null;
	public Vector<Label> labels = null;
	public Set<String> vocabulary = new HashSet<String>();
	public String trainPath = null;
	public String testPath = null;
	public String fileSeparator = System.getProperties().getProperty(
			"file.separator");

	public int findMax(double[] values) {
		double max = values[0];
		int mark = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) {
				max = values[i];
				mark = i;
			}
		}
		return mark;
	}

	public String[] sort(String[] pData, int left, int right) {
		String middle, strTemp;
		int i = left;
		int j = right;
		middle = pData[(left + right) / 2];
		do {
			while ((pData[i].compareTo(middle) < 0) && (i < right))
				i++;
			while ((pData[j].compareTo(middle) > 0) && (j > left))
				j--;
			if (i <= j) {
				strTemp = pData[i];
				pData[i] = pData[j];
				pData[j] = strTemp;
				i++;
				j--;
			}

		} while (i < j);// 如果两边扫描的下标交错，完成一次排序
		if (left < j)
			sort(pData, left, j); // 递归调用
		if (right > i)
			sort(pData, i, right); // 递归调用
		return pData;
	}

	/**
	 * 训练语料文件目录
	 * @param folderPat
	 */
	public void setTrainPath(String folderPat) {
		trainPath = folderPat;
	}

	/**
	 * 测试文件
	 * @param testPat
	 */
	public void setTestPath(String testPat) {
		testPath = testPat;
	}

	/**
	 * 训练
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void train() {
		long startTime = System.currentTimeMillis();
		File folder = new File(trainPath);
		String path = "";
		labelsName = folder.list();
		labels = new Vector<Label>();
		for (int i = 0; i < labelsName.length; i++) {
			labels.add(new Label());
			File subFolder = new File(trainPath + fileSeparator + labelsName[i]);
			String[] files = subFolder.list();
			// System.out.println("正在处理："+labelsName[i]);
			BayesClassificationGUI.setTextArea("正在处理：" + labelsName[i]);
			Vector<String> v = new Vector<String>();
			for (int j = 0; j < files.length; j++) {
				path = trainPath + fileSeparator + labelsName[i]
						+ fileSeparator + files[j];
				System.out.println(path);
				v.addAll(Seg.token(path));
			}
			// 把当前类别标签下的所有文档的所有单词都放入Set集合中，
			// 目的是为了获得文档中词的数量
			vocabulary.addAll(v);
			// 对当前类别标签下的所有文档的所有单词进行排序，
			// 目的是为了方便接下来统计各个单词的信息
			String[] allWords = new String[v.size()];
			for (int j = 0; j < v.size(); j++)
				allWords[j] = v.elementAt(j);
			sort(allWords, 0, v.size() - 1);
			// 统计各个词的信息
			String previous = allWords[0];
			double count = 1;
			Map<String, WordItem> m = new HashMap<String, WordItem>();
			for (int j = 1; j < allWords.length; j++) {
				if (allWords[j].equals(previous))
					count++;
				else {
					m.put(previous, new WordItem(count));
					previous = allWords[j];
					count = 1;
				}
			}
			labels.elementAt(i).set(m, v.size(), files.length);
			long endTime = System.currentTimeMillis();
			trainTime = endTime - startTime;
		}
		// 开始计算词频
		for (int i = 0; i < labels.size(); i++) {
			Iterator iter = labels.elementAt(i).map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, WordItem> entry = (Map.Entry<String, WordItem>) iter
						.next();
				WordItem item = entry.getValue();
				item.setFrequency(Math.log10((item.count + 1)
						/ (labels.elementAt(i).wordCount + vocabulary.size())));
			}
		}
	}

	/**
	 * 测试
	 */
	public void test() {
		Vector<String> v = null;
		v = Seg.token(testPath);
		double values[] = new double[labelsName.length];
		for (int i = 0; i < labels.size(); i++) {
			double tempValue = labels.elementAt(i).documentCount;
			for (int j = 0; j < v.size(); j++) {
				if (labels.elementAt(i).map.containsKey(v.elementAt(j))) {
					tempValue += labels.elementAt(i).map.get(v.elementAt(j)).frequency;
				} else {
					tempValue += Math
							.log10(1 / (double) (labels.elementAt(i).wordCount + vocabulary
									.size()));
				}
			}
			values[i] = tempValue;
		}

		int maxIndex = findMax(values);
		System.out.println(testPath + " 属于：" + labelsName[maxIndex]);
		BayesClassificationGUI.setTextArea(testPath + " 属于："
				+ labelsName[maxIndex]);
		label = labelsName[maxIndex];
	}

	/**
	 * 获取类别标签名
	 * 
	 * @return
	 */
	public String getLabelName() {
		return label;
	}

	/**
	 * 获取训练时间
	 * 
	 * @return
	 */
	public long getTrainingTime() {
		return trainTime;
	}
}
