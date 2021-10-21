<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	
	.qna{
		text-align: center;
		position: relative;
		top:20%;
		width:99%;
		margin: 0 auto;
		
	}
	.faq{
		display: inline-block;
		width : 800px;
		height : 300px;
		margin: 10px;
		border : 1px solid black;
	}
	.faq_type{
		display: inline-block;
		width : 200px;
		margin: 5px;
		text-align: center;
		float: left;
	}
	.faq_type tbody{
		display: inline-block;
	}
	.faq_list{
		display: inline-block;
		width : 580px;
		margin: 5px;
	}
	.faq_list tbody{
		display: inline-block;
	}
	.qna_list{
		width : 800px;
		height : 200px;
		display: inline-block;
		border : 1px solid black;
	}
	.qna_table{
		margin: 10px;
		width:780px;
	}
	.qna_button{
		
	}
</style>
</head>

<body>
	<header><%@ include file="form/header.jsp" %></header>
	<br>
	<div class="qna">
		<div class="faq">
			<table class="faq_type" style="overflow: auto; max-height: 280px;">
				<tr>
					<th>유형</th>	
				</tr>
				<tr>
					
				</tr>
			</table>
			<table class="faq_list" style="overflow: auto; max-height: 280px;">
				<tr>
					<th>자주하는 질문 리스트</th>
				</tr>
				<tr>
					<td></td>
				</tr>
								
			</table>
		</div>
		<br>
		<div class="qna_list">
			<table class="qna_table" border="1" style="overflow: auto; max-height: 180px;">
				<tr>
					<th style="width:50px;">NO.</th>
					<th>제목</th>
					<th>답변상태</th>
				</tr>
				<tr>
				</tr>
			</table>
		</div>
		<br>
		<div style="text-align: right; width: 800px; display: inline-block;">
			<input type="button" value="1대1 문의" class="qna_button">
		</div>
	</div>
	<br><br>
	<footer><%@ include file="form/footer.jsp" %></footer>
</body>
</html>