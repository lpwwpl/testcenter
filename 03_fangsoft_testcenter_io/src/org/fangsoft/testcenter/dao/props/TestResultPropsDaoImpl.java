package org.fangsoft.testcenter.dao.props;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.dao.DaoIOConfig;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.TestResult;

public class TestResultPropsDaoImpl implements TestResultDao {

	private static final TestResultDao tdao = new TestResultPropsDaoImpl();

	public static final TestResultDao getInstance() {
		return tdao;
	}

	@Override
	public List<TestResult> findTestResultByCustomer(String userId) {
		// TODO Auto-generated method stub
		List<TestResult> trs = new ArrayList<TestResult>();
		String path = DaoIOConfig.getTestResultFilePath();
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0)
			return null;
		for (File f : files) {
			String fileName = f.getName();
			if (fileName.startsWith(userId)) {
				Properties ps = new Properties();
				try {
					ps.load(new FileReader(f));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				trs.add(Property2Object.toTestResult(ps));
			}
		}
		return trs;
	}

	@Override
	public void save(TestResult tr) {
		// TODO Auto-generated method stub
		String path = DaoIOConfig.getTestResultFilePath();
		new File(path).mkdirs();
		String testResultOfCustomer = path + tr.getCustomer().getUserId() + "/";
		new File(testResultOfCustomer).mkdirs();
		Properties ps = Property2Object.toProperties(tr);
		try {
			ps.store(new FileWriter(testResultOfCustomer
					+ DaoIOConfig.getTestResultFileName(tr)), "");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		List<QuestionResult> qrs = tr.getQuestionResults();
		if (qrs != null && qrs.size() > 0) {
			String cpath = testResultOfCustomer + tr.getTest().getName() + "-"
					+ Configuration.getDateFormat().format(tr.getEndTime())
					+ "/";
			new File(cpath).mkdirs();
			try {
				for (QuestionResult qr : qrs) {
					Properties cps = Property2Object.toProperties(qr);
					String cfileName = DaoIOConfig
							.getQuestionResultFileName(qr);
					cps.store(new FileWriter(cpath + cfileName), "");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
