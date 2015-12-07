package cn.edu.kmust.nlp.gui;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import cn.edu.kmust.nlp.classifier.BayesClassification;

/**
 * 
 * @author  Zhao Shiyu
 * 
 * 分类器GUI
 *
 */
public class BayesClassificationGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private static BayesClassification bayes = new BayesClassification();
	private static JTextArea textArea;
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 500;
	private boolean flagTrain = false;
	private boolean flagTest = false;
	private static JPanel panel = null;

	public BayesClassificationGUI() {
		super("朴素贝叶斯文本分类器     赵世瑜");
		getContentPane().setLayout(null);
		setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		final JButton trainButton = new JButton();
		trainButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int n = chooser.showOpenDialog(getContentPane());
				if (n == JFileChooser.APPROVE_OPTION) {
					System.out.println(chooser.getSelectedFile().getPath());
					bayes.setTrainPath(chooser.getSelectedFile().getPath());
					flagTrain = true;
				}
			}
		});
		trainButton.setText("选择分类器训练样本");
		trainButton.setBounds(90, 30, 155, 30);
		getContentPane().add(trainButton);

		final JButton testButton = new JButton();
		testButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final JFrame f = new JFrame();
				FileDialog fd = new FileDialog(f, "打开文件对话框", FileDialog.LOAD);
				fd.setVisible(true);
				String str = fd.getDirectory() + fd.getFile();
				bayes.setTestPath(str);
				flagTest = true;
			}
		});
		testButton.setText("选择测试文本");
		testButton.setBounds(350, 30, 120, 30);
		getContentPane().add(testButton);

		textArea = new JTextArea();
		textArea.setBounds(10, 90, 560, 350);
		getContentPane().add(textArea);

		setVisible(true);

		Toolkit tk = this.getToolkit();
		Dimension dm = tk.getScreenSize();
		this.setLocation((int) (dm.getWidth() - DEFAULT_WIDTH) / 2,
				(int) (dm.getHeight() - DEFAULT_HEIGHT) / 2);
	}

	public static void setTextArea(String s) {
		textArea.insert(s + "\n", 0);
	}

	@Override
	public void run() {
		while (true) {
			if (flagTrain) {
				bayes.train();
				flagTrain = false;
				JOptionPane.showMessageDialog(panel,
						"训练完成！\n用时：" + bayes.getTrainingTime() + " ms");
			}
			if (flagTest) {
				bayes.test();
				flagTest = false;
				JOptionPane.showMessageDialog(panel, "测试完成！结果如下：\n文本类别： "
						+ bayes.getLabelName());
			}
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BayesClassificationGUI gui = new BayesClassificationGUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		Thread t = new Thread(gui);
		t.start();
	}

}
