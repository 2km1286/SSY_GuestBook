<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String check = request.getParameter("check");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/beforelogin.css">

<style>
  
	body 
	{
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  height: 100vh;
	  background-color: #f2f2f2;
	  background-image: url(images/back.png);
		background-repeat: repeat;
		background-size: auto;
	}
    
</style>

<script type="text/javascript">
	
	function login()
	{
		var f = document.myForm;
		
		f.action = "syb";
		f.submit();
	}
	
</script>

</head>
<body>

<form name="myForm" method="post">

	<%if (check.equals("findid")){ %>
		<div class="container">
		    <div class="form-group">
		        <h2>아이디 찾기 결과</h2>
		        <p style="text-align: center;">회원가입시 입력한 아이디는 <strong>${param.userId }</strong> 입니다.</p>
			</div>
			
			<div class="btn-group">
		        <button type="button" onclick="login()">로그인 화면으로 돌아가기</button>
		    </div>
		</div>
	<%}else if (check.equals("findpwd")){ %>
		<div class="container">
		    <div class="form-group">
		        <h2>비밀번호 찾기 결과</h2>
		        <p style="text-align: center;">회원가입시 입력한 비밀번호는 <strong>${param.userPwd }</strong> 입니다.</p>
			</div>
			
			<div class="btn-group">
		        <button type="button" onclick="login()">로그인 화면으로 돌아가기</button>
		    </div>
		</div>
	<%}else if (check.equals("errorId")){ %>
		<div class="container">
		    <div class="form-group">
		        <h2>아이디 찾기 결과</h2>
		        <p style="text-align: center;">존재하지 않는 닉네임 입니다.</p>
			</div>
			
			<div class="btn-group">
		        <button type="button" onclick="login()">로그인 화면으로 돌아가기</button>
		    </div>
		</div>
	<%}else if (check.equals("errorPwd")){ %>
		<div class="container">
		    <div class="form-group">
		        <h2>비밀번호 찾기 결과</h2>
		        <p style="text-align: center;">존재하지 않는 닉네임 또는 아이디 입니다.</p>
			</div>
			
			<div class="btn-group">
		        <button type="button" onclick="login()">로그인 화면으로 돌아가기</button>
		    </div>
		</div>
	<%} %>
</form>

</body>
</html>