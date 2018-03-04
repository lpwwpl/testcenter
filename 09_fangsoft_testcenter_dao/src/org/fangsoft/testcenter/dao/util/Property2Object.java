package org.fangsoft.testcenter.dao.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.DataValidator;

public class Property2Object {
	public static Properties toProperties(Test test) {
		Properties ps = new Properties();
		ps.setProperty("class", test.getClass().getName());
		ps.setProperty("name", test.getName());
		ps.setProperty("timeLimitMin", String.valueOf(test.getTimeLimitMin()));
		ps.setProperty("numQuestion", String.valueOf(test.getNumQuestion()));
		ps.setProperty("description", DataValidator.validate(test
				.getDescription()));
		ps.setProperty("score", String.valueOf(test.getScore()));
		return ps;
	}

	public static Properties toProperties(ChoiceItem item) {
		Properties ps = new Properties();
		ps.setProperty("class", item.getClass().getName());
		ps.setProperty("name", item.getName());
		ps.setProperty("correct", String.valueOf(item.isCorrect()));
		return ps;
	}
	
	public static Properties toProperties(Customer customer) {
		Properties ps = new Properties();
		ps.setProperty("class", customer.getClass().getName());
		ps.setProperty("userId", customer.getUserId());
		ps.setProperty("password", customer.getPassword());
		ps.setProperty("email", DataValidator.validate(customer.getEmail()));
		return ps;
	}
	
	public static Properties toProperties(TestResult tr) {
		Properties ps = new Properties();
		ps.setProperty("class", tr.getClass().getName());
		
		ps.setProperty("startTime", String.valueOf(tr.getStartTime()));
		ps.setProperty("endTime", String.valueOf(tr.getEndTime()));
		ps.setProperty("testDeadTime", String.valueOf(tr.getTestDeadTime()));
		ps.setProperty("status", String.valueOf(tr.getStatus()));
		ps.setProperty("score", String.valueOf(tr.getScore()));
		ps.setProperty("pass", String.valueOf(tr.getPass()));
		try {
			String test = "";
			OutputStream output = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(output);
			out.writeObject(tr.getTest());
			test = out.toString();
			ps.setProperty("test", test);
			
			
			String customer = "";
			OutputStream output2 = new ByteArrayOutputStream();
			ObjectOutput out2 = new ObjectOutputStream(output2);
			out2.writeObject(tr.getCustomer());
			customer = out2.toString();
			ps.setProperty("customer", customer);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return ps;
	}
	
	public static Properties toProperties(Question question) {
		Properties ps = new Properties();
		ps.setProperty("class", question.getClass().getName());
		ps.setProperty("name", question.getName());
		ps.setProperty("answer", DataValidator.validate(question.getAnswer()));
		ps.setProperty("score", String.valueOf(question.getScore()));
		return ps;
	}
	
	public static Properties toProperties(QuestionResult qr) {
		Properties ps = new Properties();
		ps.setProperty("class", qr.getClass().getName());
//		ps.setProperty("question", qr.getQuestion().toString());
		ps.setProperty("answer", String.valueOf(qr.getAnswer()));
		ps.setProperty("score", String.valueOf(qr.getScore()));
		ps.setProperty("result", String.valueOf(qr.isResult()));
		
		try {
			String qustion = "";
			OutputStream output = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(output);
			out.writeObject(qr.getQuestion());
			qustion = out.toString();
			ps.setProperty("question", qustion);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ps;
	}
	
	public static Test toTest(Properties ps) {
		Test t = null;
		String className = ps.getProperty("class");
		if (className.length() == 0)
			return null;
		try {
			t = (Test) Class.forName(className).newInstance();
			t.setName(ps.getProperty("name"));
			t.setNumQuestion(DataConverter.str2Int(ps
					.getProperty("numQuestion")));
			t.setTimeLimitMin(DataConverter.str2Int(ps
					.getProperty("timeLimitMin")));
			t.setScore(DataConverter.str2Int(ps.getProperty("score")));
			t.setDescription(ps.getProperty("description"));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static Question toQuestion(Properties ps) {
		Question q = null;
		String className = ps.getProperty("class");
		try {
			q = (Question)Class.forName(className).newInstance();
			q.setName(ps.getProperty("name"));
			q.setScore(DataConverter.str2Int(ps.getProperty("score")));
			q.setAnswer(ps.getProperty("answer"));
		} catch(InstantiationException ex) {
			ex.printStackTrace();
		} catch(IllegalAccessException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return q;
	}
	
	public static ChoiceItem toChoiceItem(Properties ps) {
		ChoiceItem item = null;
		String className = ps.getProperty("class");
		try {
			item = (ChoiceItem)Class.forName(className).newInstance();
			item.setName(ps.getProperty("name"));
			item.setCorrect(Boolean.parseBoolean(ps.getProperty("correct")));
		} catch(InstantiationException ex) {
			ex.printStackTrace();
		} catch(IllegalAccessException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return item;
	}
	
	public static Customer toCustomer(Properties ps) {
		Customer customer = null;
		String className = ps.getProperty("class");
		try {
			customer = (Customer)Class.forName(className).newInstance();
			customer.setUserId(ps.getProperty("userId"));
			customer.setPassword(ps.getProperty("password"));
			customer.setEmail(ps.getProperty("email"));
		} catch(InstantiationException ex) {
			ex.printStackTrace();
		} catch(IllegalAccessException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return customer;
	}
	
	public static QuestionResult toQuestionResult(Properties ps) {
		QuestionResult qr = null;
		String className = ps.getProperty("class");
		try {
			qr = (QuestionResult)Class.forName(className).newInstance();
			
			String qustionObject = ps.getProperty("quesion");
			InputStream bais = new ByteArrayInputStream(qustionObject.getBytes());
			ObjectInputStream in = new ObjectInputStream(bais);
			Question q = (Question)in.readObject();
			
			qr.setQuestion(q);
			qr.setAnswer(ps.getProperty("answer"));
			qr.setScore(Integer.parseInt(ps.getProperty("score")));
			qr.setResult(Boolean.parseBoolean(ps.getProperty("result")));
		} catch(InstantiationException ex) {
			ex.printStackTrace();
		} catch(IllegalAccessException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return qr;
	}
	
	public static TestResult toTestResult(Properties ps) {
		TestResult tr = null;
		String className = ps.getProperty("class");
		try {
			tr = (TestResult)Class.forName(className).newInstance();

			String customerObject = ps.getProperty("customer");
			InputStream bais = new ByteArrayInputStream(customerObject.getBytes());
			ObjectInputStream in = new ObjectInputStream(bais);
			Customer customer = (Customer)in.readObject();
			tr.setCustomer(customer);
			
//			##############################
//			tr.setTest(test);
			
			tr.setStartTime(DataConverter.str2Date(ps.getProperty("startTime")));
			tr.setEndTime(DataConverter.str2Date(ps.getProperty("endTime")));
			tr.setTestDeadTime(Long.parseLong(ps.getProperty("testDeadTime")));
			tr.setPass(DataConverter.str2Int(ps.getProperty("pass")));
			tr.setScore(DataConverter.str2Int(ps.getProperty("score")));
//			tr.setStatus(DataConverter.str2Int(ps.getProperty("status")));
			
		} catch(InstantiationException ex) {
			ex.printStackTrace();
		} catch(IllegalAccessException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return tr;
	}
}
