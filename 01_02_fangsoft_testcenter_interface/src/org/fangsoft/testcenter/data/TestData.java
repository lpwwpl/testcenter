package org.fangsoft.testcenter.data;

import java.util.ArrayList;
import java.util.List;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class TestData {
	public static final String RIGHT_CHOICE = "#";
	private static final String[][] JAVA_QUESTION_LIB = {
			{ "java", "3", "10", "www.fangsoft.org��java֪ʶ����", "10" },
			{ "�й�java����������ȷ�ǣ�", "#����һ�ű������", "#����һ��ƽ̨", "#���ǿ�ƽ̨��", "#������������" },
			{ "javaѧϰ�����Բο�����վ�У�", "#java.sun.com", "#www.javaworld.com",
					"#www-130.ibm.com/developerworks", "#www.fangsoft.org" },
			{ "���һ��������private����������������ȷ���ǣ�", "���ɱ�", "ͬ��(synchronized)", "#��װ",
					"����is-a��ϵ" } };
	public static String[][] getJavaQuestionLib() {
		return JAVA_QUESTION_LIB;
	}

	public static String[][] getWebQuestionLib() {
		return WEB_QUESTION_LIB;
	}

	private static final String[][] WEB_QUESTION_LIB = {
			{ "web", "3", "10", "www.fangsoft.org��web֪ʶ����", "10" },
			{ "�й�web������ȷ�ǣ�", "#��Ӧ����HttpЭ��", "#���õ�web������ΪApache",
					"#web��̬Ӧ�ó���JSP����", "#HttpЭ����1.1��" },
			{ "Webѧϰ�����Բο�����վż��", "#java.sun.com", "#www.javaworld.com",
					"#www-130.ibm.com/developerworks", "#www.fangsoft.org" },
			{ "J2ee�е�Web����������", "#Jsp", "#servlet", "#JSF", "Custom tags" } };

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
