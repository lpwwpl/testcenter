package org.fangsoft.testcenter.dao;

import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public interface QuestionDao {

	public void addQuestionToTest(Test test,Question q);
	public boolean loadQuestion(Test test);
}
