package org.fangsoft.testcenter.dao.props;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.util.DaoIOConfig;
import org.fangsoft.testcenter.dao.util.Property2Object;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.NumberUtil;

public class QuestionPropsDaoImpl implements QuestionDao {

	private static final QuestionDao qdao = new QuestionPropsDaoImpl();

	public static final QuestionDao getInstance() {
		return qdao;
	}

	@Override
	public void addQuestionToTest(Test test, Question q) {
		// TODO Auto-generated method stub
		String path = DaoIOConfig.getQuestionFilePath(test);
		new File(path).mkdirs();
		String fileName = path + DaoIOConfig.getFileName(q);
		Properties ps = Property2Object.toProperties(q);
		try {
			ps.store(new FileWriter(fileName),"");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		List<ChoiceItem> items = q.getChoiceItems();
		if(items!=null&&items.size()>0) {
			String cpath = DaoIOConfig.getChoiceItemFilePath(test, q);
			new File(cpath).mkdirs();
			try {
				for(ChoiceItem item:items) {
					Properties cps = Property2Object.toProperties(item);
					String cfileName = DaoIOConfig.getFileName(item);
					cps.store(new FileWriter(cpath+cfileName),"");
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public boolean loadQuestion(Test test) {
		// TODO Auto-generated method stub
		int numQuestion = test.getNumQuestion();
		String path = DaoIOConfig.getQuestionFilePath(test);
		File[] files = new File(path).listFiles(DaoIOConfig.FILTER);
		if (files == null || files.length == 0) {
			return false;
		}
		int qSize = files.length;
		int[] indexs = null;
		if (qSize < numQuestion) {
			return false;
		} else {
			indexs = NumberUtil.uniRandomNumbers(0, qSize, numQuestion);
		}
		Properties ps = new Properties();
		try {
			for (int i = 0; i < numQuestion; i++) {
				ps.load(new FileReader(files[indexs[i]]));
				Question q = Property2Object.toQuestion(ps);
				q.setChoiceItems(this.loadChoiceItem(test, q));
				test.addQuestion(q);
				ps.clear();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public List<ChoiceItem> loadChoiceItem(Test t, Question q) {
		String path = DaoIOConfig.getChoiceItemFilePath(t, q);
		File[] files = new File(path).listFiles(DaoIOConfig.FILTER);
		if (files != null && files.length > 0) {
			List<ChoiceItem> items = new ArrayList<ChoiceItem>(files.length);
			Properties ps = new Properties();
			for(File f:files) {
				try {
					ps.load(new FileReader(f));
				} catch(FileNotFoundException ex) {
					ex.printStackTrace();
				} catch(IOException ex) {
					ex.printStackTrace();
				}
				items.add(Property2Object.toChoiceItem(ps));
				ps.clear();
			}
			return items;
		}
		return new ArrayList<ChoiceItem>(0);
	}
}
