package org.fangsoft.testcenter.dao.array;

import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.data.TestData;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class QuestionArrayDao implements QuestionDao {

	private static final QuestionDao qdao = new QuestionArrayDao();

	public static final QuestionDao getInstance() {
		return qdao;
	}

	@Override
	public void addQuestionToTest(Test test, Question q) {
		// TODO Auto-generated method stub
		test.addQuestion(q);
	}

	
	@Override
	public boolean loadQuestion(Test test) {
		for(int i = 0;i<TestData.allTest.length;i++) {
			String[][] data = TestData.allTest[i];
			String[] tds = data[0];
			if(test.getName().equals(tds[0])) {
				TestData.loadQuestion(test, data);	
			}
		}
//		for(Test t : allTest) {
//			if(t.getName().equals(test.getName())) {
//				allTest
//				String data[][] = TestData.getJavaQuestionLib();
//				TestData.loadQuestion(test, data);
//			} else if(test.getName().equals("web")) {
//				String data[][] = TestData.getWebQuestionLib();
//				TestData.loadQuestion(test, data);
//			}
//		}
		return true;
	}
}
