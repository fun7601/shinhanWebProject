<%@page import="java.util.List"%>
<%@page import="aproject.vo.EmpVO"%>
<%@page import="aproject.model.EmpService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	EmpService eservice = new EmpService();
	List<EmpVO> emplist = eservice.selectAll();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>직원목록</h1>
<table>
	<thead>
		<tr>
			<th>직원번호</th>
			<th>이름</th>
			<th>성</th>
			<th>이메일</th>
			<th>급여</th>
			<th>입사일</th>
			<th>전화번호</th>
			<th>직책</th>
			<th>매니져</th>
			<th>커미션</th>
			<th>부서</th>
		</tr>
	</thead>
	<tbody>
		<%  for(EmpVO emp : emplist) { %>
		<tr>
			<td><%=emp.getEMPLOYEE_ID() %></td>
			<td><%=emp.getFIRST_NAME() %></td>
			<td><%=emp.getLAST_NAME() %></td>
			<td><%=emp.getEMAIL() %></td>
			<td><%=emp.getSALARY() %></td>
			<td><%=emp.getHIRE_DATE() %></td>
			<td><%=emp.getPHONE_NUMBER() %></td>
			<td><%=emp.getJOB_ID() %></td>
			<td><%=emp.getMANAGER_ID() %></td>
			<td><%=emp.getCOMMISSION_PCT() %></td>
			<td><%=emp.getDEPARTMENT_ID() %></td>
		</tr>
		<%}%>
	</tbody>
	
</table>
</body>
</html>