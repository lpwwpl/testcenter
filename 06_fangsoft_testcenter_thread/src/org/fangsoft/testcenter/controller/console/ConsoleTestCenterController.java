package org.fangsoft.testcenter.controller.console;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.TestCenterController;
import org.fangsoft.testcenter.controller.TestTimeoutGuard;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.props.TestResultPropsDaoImpl;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.view.EndTestView;
import org.fangsoft.testcenter.view.ExitView;
import org.fangsoft.testcenter.view.IsTakeTestView;
import org.fangsoft.testcenter.view.LoginView;
import org.fangsoft.testcenter.view.ModifyAnswerView;
import org.fangsoft.testcenter.view.PresentQuestionView;
import org.fangsoft.testcenter.view.PromptEndTestView;
import org.fangsoft.testcenter.view.SelectTestView;
import org.fangsoft.testcenter.view.StartTestView;
import org.fangsoft.testcenter.view.WelcomeView;

public class ConsoleTestCenterController extends TestCenterController implements
		Observer {

	private TestTimeoutGuard testTimeoutGuard;

	@Override
	public void update(Observable observable, Object obj) {
		// TODO Auto-generated method stub
		if (observable instanceof WelcomeView) {
			this.handle((WelcomeView) observable);
		} else if (observable instanceof LoginView) {
			this.handle((LoginView) observable);
		} else if (observable instanceof IsTakeTestView) {
			this.handle((IsTakeTestView) observable);
		} else if (observable instanceof SelectTestView) {
			this.handle((SelectTestView) observable);
		} else if (observable instanceof StartTestView) {
			this.handle((StartTestView) observable);
		} else if (observable instanceof PresentQuestionView) {
			this.handle((PresentQuestionView) observable);
		} else if (observable instanceof PromptEndTestView) {
			this.handle((PromptEndTestView) observable);
		} else if (observable instanceof ModifyAnswerView) {
			this.handle((ModifyAnswerView) observable);
		} else if (observable instanceof EndTestView) {
			this.handle((EndTestView) observable);
		} else if (observable instanceof ExitView) {
			this.handle((ExitView) observable);
		} 
	}

	private void handle(WelcomeView view) {
		view.deleteObservers();
		LoginView loginView = new LoginView();
		loginView.addObserver(this);
		loginView.display();
	}

	private void handle(LoginView view) {
		String userId = view.getUserId();
		String password = view.getPassword();
		this.setCustomer(this.login(userId, password));
		if (this.getCustomer() != null) {
			IsTakeTestView isTakeTestView = new IsTakeTestView();
			isTakeTestView.addObserver(this);
			isTakeTestView.display();
			view.deleteObservers();
		} else {
			if (view.getLoginCOunt() < Configuration.MAX_LOGIN) {
				view.setError(true);
				view.display();
			} else {
				view.deleteObservers();
				new ExitView("��¼����ϵͳ�˳�").display();
			}
		}
	}

	private void handle(IsTakeTestView view) {
		view.deleteObservers();
		if (view.isTakeTest()) {
			SelectTestView stView = new SelectTestView();
			stView.setTestNames(findAllTestNames());
			stView.addObserver(this);
			stView.display();
		} else {
			 new ExitView("�û���ȡ������").display();
		}
	}

	private void handle(SelectTestView view) {
		view.deleteObservers();
		StartTestView startView = new StartTestView(this.selectTest(view
				.getTestName()));
		startView.addObserver(this);
		startView.display();
	}

	private void handle(StartTestView view) {
		view.deleteObservers();
		Test test = view.getTest();
		this.setTestResult(this.startTest(test, this.getCustomer()));
		this.setIndex(1);
		testTimeoutGuard = new TestTimeoutGuard(this);
		QuestionResult qr = this.getTestResult().getQuestionResult(
				this.getIndex() - 1);
		PresentQuestionView pqView = new PresentQuestionView(qr, this
				.getIndex(), Configuration.CHOICE_LABEL);
		pqView.addObserver(this);
		pqView.display();
	}

	private void handle(PresentQuestionView view) {
		if (this.getIndex() < this.getTestResult().getTest().getNumQuestion()) {
			this.setIndex(this.getIndex() + 1);
			view.setSequence(this.getIndex());
			view.setQuestionResult(this.getTestResult().getQuestionResult(
					this.getIndex() - 1));
			view.display();
		} else {
			view.deleteObservers();
			promptEndTest();
		}
	}

	private void handle(PromptEndTestView view) {
		String read = view.getRead();
		if(read.equals("end")) {
			view.deleteObservers();
			displayEndTestView(true);
		} else {
//			try{
				view.deleteObservers();
				int theQuestionIndex = Integer.parseInt(view.getRead())-1;
				ModifyAnswerView modifyAnswerView = new ModifyAnswerView(view.getTr().getQuestionResult(theQuestionIndex),theQuestionIndex+1);
				modifyAnswerView.addObserver(this);
				modifyAnswerView.display();
				promptEndTest();
//			} catch(Exception ex) {	
////				PromptEndTestView loopView = new PromptEndTestView(this.getTestResult());
////				loopView.addObserver(this);
//				view.display();
////				loopView.display();
//			}
		}
	}
	
	private void handle(ModifyAnswerView view) {
		System.out.println("###################");
		view.deleteObservers();
		promptEndTest();
	}

	/**
	 * �����Լ��ύ��Ҫ���̹߳���
	 * @param view
	 */
	private void handle(EndTestView view) {
		if (!this.isTestTimeout())
			this.testTimeoutGuard.setTestFinished(true);
		view.deleteObservers();
		ExitView exitView = new ExitView("lpwwpllpwwpllpwwpllpwwpl");
		exitView.addObserver(this);
		exitView.display();
	}

	public void handleTimeOut() {
		displayEndTestView(true);
	}
	
	private void handle(ExitView view) {
		System.exit(0);
	}

	private void promptEndTest() {
		PromptEndTestView view = new PromptEndTestView(this.getTestResult().getTest().getNumQuestion());
		view.setTr(this.getTestResult());
		view.addObserver(this);
		view.display();
	}

	private void promptEndTestInTime() {
		displayEndTestView(false);
	}

	//���Խ�����ʾ���ݣ�ʱ�䵽�����Լ��ύ
	private synchronized void displayEndTestView(boolean timeOut) {
		//�ύ����
		commitTest(getTestResult());
		
		//��ʾ���Ա���
		EndTestView endTestView = new EndTestView(this.getTestResult(),timeOut);
		endTestView.addObserver(this);
		endTestView.display();
	}

	public TestTimeoutGuard getTestTimeoutGuard() {
		return testTimeoutGuard;
	}

	public void setTestTimeoutGuard(TestTimeoutGuard testTimeoutGuard) {
		this.testTimeoutGuard = testTimeoutGuard;
	}
}
