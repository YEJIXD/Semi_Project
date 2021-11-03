<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<%@ page import = "com.qna.dao.QnaDao" %>
<%@ page import = "com.qna.dto.QnaDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>

<% 
	int qano = Integer.parseInt(request.getParameter("qano"));
	String qatitle = request.getParameter("qatitle");
	String qacontent = request.getParameter("qacontent");
	
	QnaDao dao = new QnaDao();
	QnaDto dto = dao.selectOne(qano);
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{
		text-align: center;
		border-collapse: separate;
  		border-spacing: 0 15px;
	}
	td input[type="text"], input[type="password"] {
    	width: 350px; height: 30px;
    	font-size: 0.8rem;
    	border: 1px solid #d1d1d1;
	}
	th {
    	margin: 10px 0 8px;
   		font-size: 1.0rem;
   		font-weight: 400;
    	text-align: left;
	}
	#wrap{
		display: flex;
  		justify-content: center;
	}
	#content{
		resize:none;
	}
	#submit, #reset{
		cursor:pointer;
	}
	#submit{
		width : 80px;
	 	height : 30px;
		background-color:rgb(75, 161, 231);
		border: 1px solid gray;
		border-radius: 5px;
	}
	#reset{
		width : 80px;
	 	height : 30px;
		background-color:#d1d1d1;
		border: 1px solid gray;
		border-radius: 5px;
	}
</style>
</head>
<body>

<h2 align="center">1 : 1 문의</h2>

<div id="wrap">
	<form action="MainController" method="post">
		<input type="hidden" name="command" value="boardupdate">
		<input type="hidden" name="qano" value="<%=dto.getQano() %>"> 
		<input type="hidden" id="qa_type" value="<%=dto.getQatype() %>"> 
		
		<table>
			<tr id="title">
				<th>제 목</th>
				<td>
					<select id="qna_category" name="qa_type" style="width:50px;height:30px;" disabled>
							<option value="price">가격</option>
							<option value="deal">거래</option>
							<option value="center">시설</option>
							<option value="etc">기타</option>
	<script type="text/javascript">							
					var selectoption = document.getElementById("qna_category");
					selectoption = selectoption.options[selectoption.selectedIndex].value;
	</script>					
					
					</select>	
					<input type="text" name="qatitle" maxlength="30" value="<%=dto.getQatitle()%>" readonly="readonly">
				</td>
			</tr>
			<tr id="content">
				<th>내 용</th>
				<td><textarea rows="10" cols="60" name="qacontent" readonly="readonly"><%=dto.getQacontent() %></textarea></td>
			</tr>
			<tr>
				<td colspan="3">
					<button type="button" id="submit" onclick="location.href='question_board_update.jsp?qano=<%=dto.getQano()%>'">수정</button>&nbsp;&nbsp;
					<button type="button" id="reset" onclick="del_btn('<%=dto.getQano()%>');">삭제</button>&nbsp;&nbsp;
					<button id="reset"  onclick="location.href='qna.jsp'">목록</button>&nbsp;
				</td>
			</tr>
		</table>	
	</form>
</div>
<div>
		<footer><%@ include file = "form/footer.jsp" %></footer>
</div>		
</body>
<script>
	function del_btn(qano){
		if (confirm("삭제하시겠습니까?") == true){    //확인
			location.href="question_board_delete.jsp?qano="+qano;
		}else{   //취소
		    return;
		}
	}
</script>
</html>