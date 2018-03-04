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
				.promptYesNo("确认参加考试吗？", "y", "是：y", "否，退出：n");
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
		output("今天是：" + Configuration.getDateFormat().format(new Date()));
	}

	public static Customer login() {
		Console.output("参加考试前请先登录，输入完成后按enter确认：");
		for (int i = 0; i < Configuration.MAX_LOGIN; i++) {
			String userId = Console.prompt("输入用户名称：");
			String password = Console.prompt("输入用户密码");
			Customer customer = findCustomer(userId, password);
			if (customer != null) {
				Console.output("欢迎" + customer.getUserId() + "光临fangsoft考试中心");
				return customer;
			}
			Console.output("用户名或密码错误不能登录，重新登录。" + "登录"
					+ Configuration.MAX_LOGIN + "次不成功，系统将退出。" + "这是" + (i + 1)
					+ "次");
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
		Console.output("输入答案：");
		return Console.read();
	}

	public static TestResult takeTest(Test test, Customer customer) {
		Console.output("========开始考试===========");
		Console.output("考试名称：%1$5s%n" + "考试简述：%2$5s%n" + "考试问题：%3$5s5%n"
				+ "考试时间：%4$5s分钟%n", test.getName(), test.getDescription(), test
				.getNumQuestion(), test.getTimeLimitMin());
		long start = System.currentTimeMillis();
		Console.output("注意你有%1$s分钟答题，现在是：%2$tT%n", test.getTimeLimitMin(),
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
		Console.output("考试结束，现在是：%1$tT%n", end);
		return tr;
	}

	public static void reportTestResult(TestResult tr) {
		Console.output("=========考试报告=========");
		long duration = (tr.getEndTime().getTime() - tr.getStartTime()
				.getTime())
				/ (1000 * 60);
		Console.output("你花了%1$s分钟考试%n", duration);
		Console
				.output("%1$-6s%2$-6s%3$-6s%4$-6s%n", "题号", "你的答案", "正确答案",
						"对错");
		int count = 0;
		for (QuestionResult qr : tr.getQuestionResults()) {
			Console.output("%1$-8s%2$-8s%3$-8s%4$-8s%n", ++count, qr
					.getAnswer(), qr.getQuestion().getAnswer(),
					qr.isResult() ? "right" : "wrong");
		}
		boolean pass = tr.getPass() > 0 ? true : false;
		String displayPass = "";
		if (pass)
			displayPass = "恭喜，你通过了考试";
		else
			displayPass = "很遗憾，你没有通过考试";
		Console.output("你的考试的得分是:" + tr.getScore() + "," + displayPass);
	}

	public static Test selectTest() {
		Console.output("fangsoft考试中心提供的所有考试：");
		TestDao tDao = TestArrayDao.getInstance();
		List<Test> allTest = tDao.findAllTest();
		int count = 0;
		for (Test t : allTest) {
			Console.output((++count) + ". " + t.getName() + "考试" + ",输入："
					+ t.getName());
		}
		while(true) {
			String testName = Console.prompt("请选择考试名称：");
			for(Test t:allTest) {
				if(t.getName().equals(testName))return t;
			}
		}
	}
}
