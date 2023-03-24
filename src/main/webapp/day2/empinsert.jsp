<%@page import="aproject.model.EmpService"%>
<%@page import="com.shinhan.dbutil.DateUtil"%>
<%@page import="aproject.vo.EmpVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
//http://localhost:9999/shinhanWebProject/day2/empinsert.jsp?EMPLOYEE_ID=111&FIRST_NAME=sdsd&LAST_NAME=sdsd&EMAIL=sdsd%40sdsd&PHONE_NUMBER=010-3666-6666
//&HIRE_DATE=2023-02-28&JOB_ID=IT_PROG&SALARY=5000&DEPARTMENT_ID=50&MANAGER_ID=100&COMMISSION_PCT=0.1
String empid = request.getParameter("EMPLOYEE_ID");
String firstname = request.getParameter("FIRST_NAME");
String lastname = request.getParameter("LAST_NAME");
String email = request.getParameter("EMAIL");
String phonenumber = request.getParameter("PHONE_NUMBER");
String hdate = request.getParameter("HIRE_DATE");
String jobid = request.getParameter("JOB_ID");
String sal = request.getParameter("SALARY");
String deptid = request.getParameter("DEPARTMENT_ID");
String mgrid = request.getParameter("MANAGER_ID");
String cmp = request.getParameter("COMMISSION_PCT");

EmpVO emp = new EmpVO();

emp.setCOMMISSION_PCT(Double.parseDouble(cmp));
emp.setDEPARTMENT_ID(Integer.parseInt(deptid));
emp.setEMAIL(email);
emp.setEMPLOYEE_ID(Integer.parseInt(empid));
emp.setFIRST_NAME(firstname);
emp.setLAST_NAME(lastname);
emp.setPHONE_NUMBER(phonenumber);
emp.setSALARY(Integer.parseInt(sal));
emp.setHIRE_DATE(DateUtil.convertToDate(hdate));
emp.setJOB_ID(jobid);
emp.setMANAGER_ID(Integer.parseInt(mgrid));

EmpService service = new EmpService();
service.empInsert(emp);


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
	p {border: 1px solid green;}
</style>

</head>
<body>
<h1>신규직원을 DB에 저장합니다.</h1>
<p>직원번호 : <%=empid %> </p>
<p>이름 : <%=firstname %> </p>
<p>성 : <%=lastname %> </p>
<p>전화번호 : <%=phonenumber %> </p>
<p>이메일 : <%=hdate %> </p>
<p>직책 : <%=jobid %> </p>
<p>연봉 : <%=sal %> </p>
<p>부서번호 : <%=deptid %> </p>
<p>매니저 아이디 : <%=mgrid %> </p>
<p>커미션 : <%=cmp %> </p>

</body>
</html>