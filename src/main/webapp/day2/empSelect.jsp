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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- <style>
		#container {
			border: 1px solid gray;
			width: 1000px;
			margin: 0 auto;
			text-align: center;
		}
		h1 {
			border: 1px solid gray;
			font-family: "맑은 고딕";
			letter-spacing: 0.5em;
			text-shadow: 6px 2px 2px gray;
			padding: 10px;
		}
		thead tr {
			background-color: lightblue;
			padding: 10px;
			font-family: "맑은 고딕"
			
		}
		tbody tr, td{
			border: 1px solid gray;
			font-family: "맑은 고딕";
			
			/*border-style:solid;
			border-color: black;
			border-bottom: medium;*/
		}
		/*.center {
			text-align: center;
			border: 1px solid black;
		}*/
		.grad {
			background: linear-gradient(to right bottom, blue, white);
		}
	
	</style> -->
<script>
	$(function() {
		$("#btn1").click(function() {
			//$("td:odd").css("backgroundColor", "lightgreen");
			
			//body > div > table > tbody > tr:nth-child(1) > td:nth-child(2) --> Steven
			//body > div > table > tbody > tr:nth-child(2) > td:nth-child(2) --> Neena
			$("tr:nth-child(2n)").css("backgroundColor", "lightgray");
			$("tr:nth-child(2n+1)").css("backgroundColor", "white");
		})
		
		$("#btn2").click(function() {
			$("tr> td:nth-child(2):contains('S')").css("color", "red");
		})
		
		$("#btn3").click(function() {
			$("tr td:contains('S')").css("color", "blue");
		})
		
		$("#btn4").on("click", function () {
			init();
			var selector = " tr > td:nth-child(5)";
			
			$(selector).each(function (index, item) {
				var sal = parseInt($(item).html());
				if(sal >= 5000) {
					$(item).css("background-color", "lightgreen")
				}
			})
		})
		
	
		$("thead tr th").click(function(e) {
			var trNum = $(this).closest("th").prevAll().length;
			//$(this).find(" ~ th").css("background-color", "white");
			//$(this).css("background-color", "orange");
			var a = $("tbody tr").each(function(index, item) {
				var col = $(item).find("td:nth-child(" + (trNum + 1) + ")");
				console.log(col);
				$(item).find("td").css("background-color", "white");
				$(col).css("background-color", "orange");
			});
		});
		
		var str = "";
		var arr = ["IT_PROG", "AD_VP", "AD_PRES", "ST_MAN", "ST_CLERK"];
		$.each(arr, function (index, item) {
			str += "<option>" + item + "</option>";
		});
		
		$("#jobs").html(str); //html() : jquery 함수, innerHTML은 자바스크립트 속성
		
		
		$("select").change(function(){
			var jobid = $(this).val();
			//init();
			$("tr td").css("color","black");
			$("tr td:contains('" + jobid + "')").css("color", "red");
			});
	})

	
</script>
</head>
<body>
	<div class="container mt-3">
		<h1 class="grad">직원목록</h1>
		<button onclick="location.href='emp_insert.html'" type="button"
			class="btn btn-success">직원등록</button>
		<a type="button" class="btn btn-success" href="emp_insert.html">직원등록</a>
		<table class="table table-hover">

			<hr>
			<button id='btn1'>짝수행만 선택</button>
			<button id='btn2'>이름이 S로 시작하는 직원</button>
			<button id='btn3'>S가 포함된 것</button>
			<button id='btn4'>연봉 5000 이상인 직원</button>
			
			<select id="jobs">
				
			</select>
			<hr>
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
				<%
				for (EmpVO emp : emplist) {
				%>
				<tr>
					<td><%=emp.getEMPLOYEE_ID()%></td>
					<td id="name"><%=emp.getFIRST_NAME()%></td>
					<td><%=emp.getLAST_NAME()%></td>
					<td><%=emp.getEMAIL()%></td>
					<td><%=emp.getSALARY()%></td>
					<td><%=emp.getHIRE_DATE()%></td>
					<td><%=emp.getPHONE_NUMBER()%></td>
					<td><%=emp.getJOB_ID()%></td>
					<td><%=emp.getMANAGER_ID()%></td>
					<td><%=emp.getCOMMISSION_PCT()%></td>
					<td><%=emp.getDEPARTMENT_ID()%></td>
				</tr>
				<%
				}
				%>
			</tbody>
			</div>

		</table>
</body>
</html>