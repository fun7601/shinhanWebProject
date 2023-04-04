<%@page import="aproject.vo.EmpVO"%>
<%@page import="aproject.model.EmpService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String sid = request.getParameter("empid");
int empid = 100;
if(sid != null) empid = Integer.parseInt(sid);

EmpService service = new EmpService();
EmpVO emp = service.selectByid(empid);
%>


{
	"firstname" : "<%=emp.getFIRST_NAME() %>",
	"age" : 20,
	"salary" : "<%=emp.getSALARY() %>"
}
