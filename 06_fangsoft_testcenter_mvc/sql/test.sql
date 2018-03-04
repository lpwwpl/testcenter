drop table test;
drop table question;
drop table customer;
drop table choiceItem;
drop table testResult;
drop table questionResult;
drop sequence "SEQ_TEST";
drop sequence "SEQ_CUSTOMER";
drop sequence "SEQ_QUESTION";
drop sequence "SEQ_QUESTIONRESULT";
drop sequence "SEQ_TESTRESULT";
drop sequence "SEQ_CHOICEITEM";
create table test (
	tt_id integer not null,
	tt_name varchar(80) not null,
	tt_numQuestion integer not null,
	tt_timelimitMin integer not null,
	tt_description varchar(255) null,
	tt_score integer null,
	primary key(tt_id)
);
create table question (
	qn_id integer not null,
	qn_name varchar(512) not null,
	qn_answer varchar(512) null,
	qn_score integer null,
	qn_test_id integer null,
	primary key(qn_id),
	foreign key(qn_test_id) references test(tt_id)
);
create table choiceItem (
	ch_id integer not null,
	ch_name varchar(512) not null,
	ch_correct integer null,
	ch_question_id integer null,
	primary key(ch_id),
	foreign key(ch_question_id) references question(qn_id)
);
create table customer (
	cm_id integer not null,
	cm_userId varchar(512) not null,
	cm_password varchar(512) not null,
	cm_email varchar(128) null,
	primary key(cm_id)
);
create table testResult (
	tr_id integer not null,
	tr_test_id integer null,
	tr_customer_id integer null,
	tr_status integer null,
	tr_score integer null,
	tr_pass integer null,
	tr_startTime date null,
	tr_endTime date null,
	primary key(tr_id),
	foreign key(tr_test_id) references test(tt_id),
	foreign key(tr_customer_id) references customer(cm_id)
	
);
create table questionResult (
	qr_id integer not null,
	qr_testResult_id integer null,
	qr_question_id integer null,
	qr_score integer null,
	qr_answer varchar(128) null,
	qr_result integer null,
	primary key(qr_id),
	foreign key(qr_question_id) references question(qn_id),
	foreign key(qr_testResult_id) references testResult(tr_id)
);
create sequence "SEQ_TEST";
create sequence "SEQ_CUSTOMER";
create sequence "SEQ_QUESTION";
create sequence "SEQ_QUESTIONRESULT";
create sequence "SEQ_TESTRESULT";
create sequence "SEQ_CHOICEITEM";