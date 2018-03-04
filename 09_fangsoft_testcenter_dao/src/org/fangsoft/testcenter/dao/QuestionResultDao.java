package org.fangsoft.testcenter.dao;

import java.util.List;

import org.fangsoft.testcenter.model.QuestionResult;

public interface QuestionResultDao {
	public List<QuestionResult> findQuestionResultByCustomer(int questionId);
	public void save(QuestionResult questionResult);
}
