package org.fangsoft.testcenter.data;

import java.util.ArrayList;
import java.util.List;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class TestData {
	public static final String RIGHT_CHOICE = "#";
	private static final String[][] JAVA_QUESTION_LIB = {
			{ "java", "3", "10", "www.fangsoft.org的java知识测试", "10" },
			{ "有关java语言论述正确是？", "#它是一门编程语言", "#它是一个平台", "#它是跨平台的", "#它是面向对象的" },
			{ "java学习常可以参考的网站有？", "#java.sun.com", "#www.javaworld.com",
					"#www-130.ibm.com/developerworks", "#www.fangsoft.org" },
			{ "如果一个属性用private声明，下面论述正确的是？", "不可变", "同步(synchronized)", "#封装",
					"代表is-a关系" } };
	public static String[][] getJavaQuestionLib() {
		return JAVA_QUESTION_LIB;
	}

	public static String[][] getWebQuestionLib() {
		return WEB_QUESTION_LIB;
	}

	private static final String[][] WEB_QUESTION_LIB = {
			{ "web", "3", "10", "www.fangsoft.org的web知识测试", "10" },
			{ "有关web论述正确是？", "#它应用了Http协议", "#常用的web服务器为Apache",
					"#web动态应用常用JSP开发", "#Http协议有1.1版" },
			{ "Web学习常可以参考的网站偶？", "#java.sun.com", "#www.javaworld.com",
					"#www-130.ibm.com/developerworks", "#www.fangsoft.org" },
			{ "J2ee中的Web开发技术有", "#Jsp", "#servlet", "#JSF", "Custom tags" } };

	public static final String[][][] allTest = { JAVA_QUESTION_LIB,
			WEB_QUESTION_LIB };

	public static Test produceTest() {
		String[] tds = JAVA_QUESTION_LIB[0];
		int numQ = JAVA_QUESTION_LIB.length;
		int numQuestion = Integer.parseInt(tds[1]);
		if (numQuestion > (numQ - 1))
			numQuestion = numQ - 1;
		Test test = new Test();
		test.setName(tds[0]);
		test.setNumQuestion(numQuestion);
		test.setTimeLimitMin(Integer.parseInt(tds[2]));
		test.setDescription(tds[3]);
		test.setScore(Integer.parseInt(tds[4]));
		int qi = 0;
		while (qi < numQuestion) {
			String[] qds = JAVA_QUESTION_LIB[qi + 1];
			Question q = new Question();
			q.setName(qds[0]);
			List<ChoiceItem> items = new ArrayList<ChoiceItem>();
			for (int j = 1; j < qds.length; j++) {
				ChoiceItem ch = new ChoiceItem();
				String choiceText = qds[j];
				if (choiceText.indexOf(RIGHT_CHOICE) == 0) {
					choiceText = choiceText.substring(1);
					ch.setCorrect(true);
				}
				ch.setName(choiceText);
				items.add(ch);
			}
			q.setChoiceItems(items);
			q.setScore(1);
			test.addQuestion(q);
			qi++;
		}
		return test;
	}

	public static Test newTest(String[][] data) {
		Test test = new Test();
		String[] tds = data[0];
		int numQuestion = Integer.parseInt(tds[1]);
		int numQ = data.length;
		if(numQuestion>(numQ-1))numQuestion = numQ-1;
		test.setName(tds[0]);
		test.setNumQuestion(numQuestion);
		test.setTimeLimitMin(Integer.parseInt(tds[2]));
		test.setDescription(tds[3]);
		test.setScore(Integer.parseInt(tds[4]));
		return test;
	}

	public static void loadQuestion(Test test, String[][] data) {
		int qi = 0;
		while(qi<data.length-1) {
			String[] qds = data[qi+1];
			Question q = new Question();
			q.setName(qds[0]);
			for(int j = 1;j<qds.length;j++) {
				ChoiceItem ch = new ChoiceItem();
				String choiceText = qds[j];
				if(choiceText.startsWith(RIGHT_CHOICE)) {
					choiceText = choiceText.substring(1);
					ch.setCorrect(true);
				}
				ch.setName(choiceText);
				q.addChoiceItem(ch);
			}
			q.setScore(1);
			test.addQuestion(q);
			qi++;
		}
	}
}
