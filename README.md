# 朴素贝叶斯中文文本分类器

## 语料与分词
- 语料使用搜狗文本分类语料[mini版](http://www.sogou.com/labs/dl/c.html)
- 中文分词使用[ansj_seg](https://github.com/NLPchina/ansj_seg)

## 使用

  ```
  mvn clean package
  java -jar target/TextClassify-0.0.1-SNAPSHOT.jar
  ```
  如果想使用eclipse查看代码，可以通过:
  ```
  mvn eclipse:eclipse  
  ```
  生成eclipse工程
