package org.fangsoft.testcenter.controller.console;

import java.util.Observable;
import java.util.Observer;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.controller.TestCenterController;
import org.fangsoft.testcenter.controller.TestTimeoutGuard;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.view.EndTestView;
import org.fangsoft.testcenter.view.ExitView;
import org.fangsoft.testcenter.view.IsTakeTestView;
import org.fangsoft.testcenter.view.LoginView;
import org.fangsoft.testcenter.view.PresentQuestionView;
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
				// new ExitView("��¼����ϵͳ�˳�").display();
			}
		}
	}

	private void handle(IsTakeTestView view) {
		view.deleteObservers();
		if (view.isTakeTest()) {
			SelectTestView stView = new SelectTestView();
			stView.addObserver(this);
			stView.display();
		} else {
			// new ExitView("��¼����ϵͳ�˳�").display();
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
		this.testTimeoutGuard = new TestTimeoutGuard(this);
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

	private void handle(EndTestView view) {
		if (!this.isTestTimeout())
			this.testTimeoutGuard.setTestFinished(true);
	}

	public void handleTimeOut() {
		displayEndTestView(true);
	}
	
	private void handle(ExitView view) {
		System.exit(0);
	}

	private void promptEndTest() {

	}

	private synchronized void displayEndTestView(boolean timeOut) {
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
