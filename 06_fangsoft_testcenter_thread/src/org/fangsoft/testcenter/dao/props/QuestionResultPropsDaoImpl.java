package org.fangsoft.testcenter.dao.props;

import java.util.List;

import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.model.QuestionResult;

public class QuestionResultPropsDaoImpl implements QuestionResultDao {
	private static final QuestionResultDao qdao = new QuestionResultPropsDaoImpl();

	public static final QuestionResultDao getInstance() {
		return qdao;
	}

	@Override
	public List<QuestionResult> findQuestionResultByCustomer(int questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(QuestionResult questionResult) {
		// TODO Auto-generated method stub
		
	}
}
