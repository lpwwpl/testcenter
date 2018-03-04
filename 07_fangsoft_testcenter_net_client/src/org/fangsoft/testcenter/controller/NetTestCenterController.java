package org.fangsoft.testcenter.controller;

import java.util.List;

import org.fangsoft.testcenter.command.CommitTestCommand;
import org.fangsoft.testcenter.command.DisplayAllTestNamesCommand;
import org.fangsoft.testcenter.command.ICommand;
import org.fangsoft.testcenter.command.LoginCommand;
import org.fangsoft.testcenter.command.SelectTestCommand;
import org.fangsoft.testcenter.command.StartTestCommand;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.testcenter.net.client.NetClient;

public class NetTestCenterController extends TestCenterController {
	private NetClient<ICommand, ICommand> netClient;

	public NetTestCenterController() {

	}

	public NetTestCenterController(NetClient<ICommand, ICommand> netClient) {
		this.netClient = netClient;
	}

	public Customer login(String userId, String password) {
		LoginCommand request = new LoginCommand(userId, password);
		LoginCommand response = (LoginCommand) getNetClient().synProcess(
				request);
		this.setCustomer(response.getCustomer());
		return response.getCustomer();
	}

	public List<String> findAllTestNames() {
		DisplayAllTestNamesCommand request = new DisplayAllTestNamesCommand();
		DisplayAllTestNamesCommand response = (DisplayAllTestNamesCommand) getNetClient()
				.synProcess(request);
		
		return response.getTestNames();
	}

	public Test selectTest(String testName) {
		SelectTestCommand request = new SelectTestCommand(testName);
		SelectTestCommand response = (SelectTestCommand) this.getNetClient()
				.synProcess(request);
		return response.getTest();
	}

	public TestResult startTest(Test test, Customer customer) {
		StartTestCommand request = new StartTestCommand(test, customer);
		StartTestCommand response = (StartTestCommand) getNetClient()
				.synProcess(request);
		TestResult tr = response.getTr();
		this.setTestDeadTime(tr.getStartTime().getTime()
				+ test.getTimeLimitMin() * 1000 * 60);
		return tr;
	}

	public void commitTest(TestResult tr) {
		CommitTestCommand request = new CommitTestCommand(tr);
		CommitTestCommand response = (CommitTestCommand) this.getNetClient()
				.synProcess(request);
		this.setTestResult(response.getTr());
	}
	

	public NetClient<ICommand, ICommand> getNetClient() {
		return netClient;
	}

	public void setNetClient(NetClient<ICommand, ICommand> netClient) {
		this.netClient = netClient;
	}
}
