package org.fangsoft.testcenter.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.DataConverter;

public class ResultSet2Object {

	public static Test rs2Test(ResultSet rs) throws SQLException {
		Test t = new Test();
		t.setId(rs.getInt("tt_id"));
		t.setName(rs.getString("tt_name"));
		t.setNumQuestion(rs.getInt("tt_numQuestion"));
		t.setTimeLimitMin(rs.getInt("tt_timelimitMin"));
		t.setDescription(rs.getString("tt_description"));
		t.setScore(rs.getInt("tt_score"));
		return t;
	}

	public static Question rs2Question(ResultSet rs) throws SQLException {
		Question q = new Question();
		q.setId(rs.getInt("qn_id"));
		q.setName(rs.getString("qn_name"));
		q.setScore(rs.getInt("qn_score"));
		q.setAnswer(rs.getString("qn_answer"));
		q.setTestId(rs.getInt("qn_test_id"));
		return q;
	}

	public static ChoiceItem rs2ChoiceItem(ResultSet rs) throws SQLException {
		
		ChoiceItem ch = new ChoiceItem();
		ch.setId(rs.getInt("ch_id"));
		ch.setName(rs.getString("ch_name"));
		ch.setCorrect(DataConverter.int2Boolean(rs.getInt("ch_correct")));
		ch.setQuestionId(rs.getInt("ch_question_id"));
		return ch;
	}

	public static TestResult rs2TestResult(ResultSet rs) throws SQLException {
		TestResult tr = new TestResult();
		tr.setId(rs.getInt("tr_id"));
		tr.setStartTime(new Date(rs.getDate("tr_startTime").getTime()));
		tr.setEndTime(new Date(rs.getDate("tr_endTime").getTime()));
		tr.setScore(rs.getInt("tr_score"));
		tr.setStatus(Field2Property.int2TestResultStatus(rs.getInt("tr_status")));
		tr.setPass(rs.getInt("pass"));
		tr.setTestId(rs.getInt("tr_test_id"));
		tr.setCustomerId(rs.getInt("tr_customer_id"));
		return tr;
	}

	public static QuestionResult rs2QuestionResult(ResultSet rs) throws SQLException {
		QuestionResult qr = new QuestionResult();
		qr.setId(rs.getInt("qr_id"));
		qr.setAnswer(rs.getString("answer"));
		qr.setQuestionId(rs.getInt("qr_question_id"));
		qr.setResult(DataConverter.int2Boolean(rs.getInt("qr_result")));
		qr.setScore(rs.getInt("qr_score"));
		qr.setTestResultId(rs.getInt("qr_testResult_id"));
		return null;
	}

	public static Customer rs2Customer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getInt("cm_id"));
		customer.setUserId(rs.getString("cm_userId"));
		customer.setPassword(rs.getString("cm_password"));
		customer.setEmail(rs.getString("cm_email"));
		return customer;
	}
}
