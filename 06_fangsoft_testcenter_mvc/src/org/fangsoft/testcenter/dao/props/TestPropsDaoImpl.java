package org.fangsoft.testcenter.dao.props;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.util.DaoIOConfig;
import org.fangsoft.testcenter.dao.util.Property2Object;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class TestPropsDaoImpl implements TestDao {

	private static final TestDao tdao = new TestPropsDaoImpl();

	public static final TestDao getInstance() {
		return tdao;
	}

	@Override
	public List<Test> findAllTest() {
		// TODO Auto-generated method stub
		String testPath = DaoIOConfig.getTestFilePath();
		File[] files = new File(testPath).listFiles();
		if (files == null || files.length == 0)
			return new ArrayList<Test>(0);
		ArrayList<Test> tests = new ArrayList<Test>(files.length);
		Properties ps = new Properties();
		try {
			for (File f : files) {
				if (f.isDirectory())
					continue;
				ps.load(new FileReader(f));
				tests.add(Property2Object.toTest(ps));
				ps.clear();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		tests.trimToSize();
		return tests;
	}

	@Override
	public Test findTestByName(String testName) {
		// TODO Auto-generated method stub
		String path = DaoIOConfig.getTestFilePath();
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0)
			return null;
		for (File f : files) {
			String fileName = f.getName();
			if (f.isFile() && fileName.startsWith(testName)) {
				Properties ps = new Properties();
				try {
					ps.load(new FileReader(f));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				return Property2Object.toTest(ps);
			}
		}
		return null;
	}

	@Override
	public void save(Test test) {
		// TODO Auto-generated method stub
		Properties ps = Property2Object.toProperties(test);
		String path = DaoIOConfig.getTestFilePath();
		String fileName = DaoIOConfig.getFileName(test);
		new File(path).mkdir();
		try {
			ps.store(new FileWriter(path + fileName), "");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		List<Question> qList = test.getQuestions();
		if (qList != null) {
			for (Question q : qList) {
				QuestionPropsDaoImpl.getInstance().addQuestionToTest(test, q);
			}
		}
	}

	@Override
	public List<String> findAllTestNames() {
		List<String> ret = new ArrayList<String>();
		String path = DaoIOConfig.getTestFilePath();
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0)
			return null;
		for (File f : files) {
			if (f.isFile()) {
				Properties ps = new Properties();
				try {
					ps.load(new FileReader(f));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				Test test = Property2Object.toTest(ps);
				ret.add(test.getName());
			}
		}
		return ret;
	}

}
