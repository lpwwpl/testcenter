package org.fangsoft.testcenter;

import static org.fangsoft.util.Console.output;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.console.ConsoleTestCenterController;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.array.QuestionArrayDao;
import org.fangsoft.testcenter.dao.array.TestArrayDao;
import org.fangsoft.testcenter.dao.props.TestResultPropsDaoImpl;
import org.fangsoft.testcenter.data.CustomerData;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.testcenter.view.WelcomeView;
import org.fangsoft.util.Console;

public class TestCenter {

	public static void main(String[] args) {
		welcome();
		Customer customer = login();
		if (customer == null) {

		}
		boolean response = Console
				.promptYesNo("ȷ�ϲμӿ�����", "y", "�ǣ�y", "���˳���n");
		if (!response) {
		}
		Test test = selectTest();
//		Test test = TestData.produceTest();
		TestResult tr = takeTest(test, customer);
		reportTestResult(tr);
		
		TestResultDao trDao = TestResultPropsDaoImpl.getInstance();
		trDao.save(tr);		
	}

	public static void welcome() {
		output("�����ǣ�" + Configuration.getDateFormat().format(new Date()));
	}

	public static Customer login() {
		Console.output("�μӿ���ǰ���ȵ�¼��������ɺ�enterȷ�ϣ�");
		for (int i = 0; i < Configuration.MAX_LOGIN; i++) {
			String userId = Console.prompt("�����û����ƣ�");
			String password = Console.prompt("�����û�����");
			Customer customer = findCustomer(userId, password);
			if (customer != null) {
				Console.output("��ӭ" + customer.getUserId() + "����fangsoft��������");
				return customer;
			}
			Console.output("�û�������������ܵ�¼�����µ�¼��" + "��¼"
					+ Configuration.MAX_LOGIN + "�β��ɹ���ϵͳ���˳���" + "����" + (i + 1)
					+ "��");
		}
		return null;
	}

	private static Customer findCustomer(String userId, String password) {

		Customer[] customers = CustomerData.getCustomers();
		for (int i = 0; i < customers.length; i++) {
			Customer c = customers[i];
			if (c.getUserId().equals(userId)
					&& c.getPassword().equals(password)) {
				return customers[i];
			}

		}
		return null;
	}

	private static void exit(Object msg) {
		output(msg);
		System.exit(1);
	}

	public static void runMVC() {
		ConsoleTestCenterController tcc = new ConsoleTestCenterController();
		tcc.setDaoFactory(Configuration.getDBDaoFactory());
		WelcomeView view = new WelcomeView();
		view.addObserver(tcc);
		view.display();
	}

	private static TestResult newTestResult(Customer c, Test test) {
		TestResult tr = new TestResult();
		tr.setCustomer(c);
		tr.setTest(test);
		tr.setStartTime(new Date());
		// ############
		tr.setQuestionResults(new ArrayList<QuestionResult>());
		for (Question q : test.getQuestions()) {
			q.assignLabel(Configuration.CHOICE_LABEL);
			QuestionResult qr = new QuestionResult();
			qr.setQuestion(q);
			tr.getQuestionResults().add(qr);
		}
		return tr;
	}

	public static String presentQuestion(int pos, Question q) {
		Console.output("%1$s.%2$s%n", pos, q.getName());
		for (ChoiceItem item : q.getChoiceItems()) {
			Console.output("	%1$s.%2$s%n", item.getLabel(), item.getName());
		}
		Console.output("����𰸣�");
		return Console.read();
	}

	public static TestResult takeTest(Test test, Customer customer) {
		Console.output("========��ʼ����===========");
		Console.output("�������ƣ�%1$5s%n" + "���Լ�����%2$5s%n" + "�������⣺%3$5s5%n"
				+ "����ʱ�䣺%4$5s����%n", test.getName(), test.getDescription(), test
				.getNumQuestion(), test.getTimeLimitMin());
		long start = System.currentTimeMillis();
		Console.output("ע������%1$s���Ӵ��⣬�����ǣ�%2$tT%n", test.getTimeLimitMin(),
				start);
//		TestResult tr = newTestResult(customer, test);
		QuestionArrayDao.getInstance().loadQuestion(test);
		TestResult tr = newTestResult(customer, test);
		int count = 0;
		for (QuestionResult qt : tr.getQuestionResults()) {

			String answer = presentQuestion(++count, qt.getQuestion());
			qt.setAnswer(answer);
		}
		long end = System.currentTimeMillis();
		tr.setEndTime(new Date(end));
		tr.commitTest();
		Console.output("���Խ����������ǣ�%1$tT%n", end);
		return tr;
	}

	public static void reportTestResult(TestResult tr) {
		Console.output("=========���Ա���=========");
		long duration = (tr.getEndTime().getTime() - tr.getStartTime()
				.getTime())
				/ (1000 * 60);
		Console.output("�㻨��%1$s���ӿ���%n", duration);
		Console
				.output("%1$-6s%2$-6s%3$-6s%4$-6s%n", "���", "��Ĵ�", "��ȷ��",
						"�Դ�");
		int count = 0;
		for (QuestionResult qr : tr.getQuestionResults()) {
			Console.output("%1$-8s%2$-8s%3$-8s%4$-8s%n", ++count, qr
					.getAnswer(), qr.getQuestion().getAnswer(),
					qr.isResult() ? "right" : "wrong");
		}
		boolean pass = tr.getPass() > 0 ? true : false;
		String displayPass = "";
		if (pass)
			displayPass = "��ϲ����ͨ���˿���";
		else
			displayPass = "���ź�����û��ͨ������";
		Console.output("��Ŀ��Եĵ÷���:" + tr.getScore() + "," + displayPass);
	}

	public static Test selectTest() {
		Console.output("fangsoft���������ṩ�����п��ԣ�");
		TestDao tDao = TestArrayDao.getInstance();
		List<Test> allTest = tDao.findAllTest();
		int count = 0;
		for (Test t : allTest) {
			Console.output((++count) + ". " + t.getName() + "����" + ",���룺"
					+ t.getName());
		}
		while(true) {
			String testName = Console.prompt("��ѡ�������ƣ�");
			for(Test t:allTest) {
				if(t.getName().equals(testName))return t;
			}
		}
	}
}
