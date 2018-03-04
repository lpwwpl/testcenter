package org.fangsoft.testcenter.dao.db;

import java.util.Properties;

import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class Property2Object {
	public static Properties toProperties(Test test) {
		Properties ps = new Properties();
		ps.setProperty("class", test.getClass().getName());
		ps.setProperty("name", test.getName());
		ps.setProperty("timeLimitMin", String.valueOf(test.getTimeLimitMin()));
		ps.setProperty("numQuestion", String.valueOf(test.getNumQuestion()));
//		ps.setProperty("description", Validator.)
		ps.setProperty("score", String.valueOf(test.getScore()));
		return ps;
	}
	public static Test toTest(Properties ps) {
		Test t = null;
		String className = ps.getProperty("class");
		if(className.length()==0)return null;
		try{
			t=(Test)Class.forName(className).newInstance();
			t.setName(ps.getProperty("name"));
//			t.setNumQuestion(DataConverter.str2Int());
//			t.set
		}catch(InstantiationException e) {
			e.printStackTrace();
		}catch(IllegalAccessException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}
	public static Question toQuestion(Properties ps) {
		Question q = null;
		String className = ps.getProperty("class");
		if(className.length()==0)return null;
		try{
			q=(Question)Class.forName(className).newInstance();
			q.setName(ps.getProperty("name"));
//			q.setScore(score)
			q.setAnswer(ps.getProperty("answer"));
		}catch(InstantiationException e) {
			e.printStackTrace();
		}catch(IllegalAccessException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return q;
	}
}
