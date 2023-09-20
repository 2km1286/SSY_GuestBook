 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String error = request.getParameter("error");

	session.removeAttribute("userId");
	session.removeAttribute("userPwd");
%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 페이지</title>

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

	function check()
	{
		if (<%=error %>)
			alert("아이디 또는 비밀번호가 일치하지 않습니다.");
		
	}

	function login()
	{
		var f = document.myForm;
		
		if (f.userId.value == "")
		{
			alert("아이디를 입력하세요.");
			f.userId.focus();
			return;
		}
		
		if (f.userPwd.value == "")
		{
			alert("비밀번호를 입력하세요.");
			f.userPwd.focus();
			return;
		}
		
		f.key.value = "login";
		"WebContent/WEB-INF/view/login2.jsp"
		f.action = "boardConn";
		
		f.submit();
	}
	
	function member()
	{
		var f = document.myForm;
		
		f.key.value = "member";
		
		f.action ="boardConn";
		
		f.submit();
	}
	
	function findInfo()
	{
		var f = document.myForm;
		
		f.key.value = "findinfo";
		
		f.action = "boardConn";
		
		f.submit();
	}

</script>
  
</head>
<body onload="check()">

	<form name="myForm" method="post">
	
		<div class="logo">
           <img src="images/loginlogo.png">
        </div>
         
		<div class="container">
		 
			<h2>방명록 로그인</h2>
	   
			<div class="form-group">
				<label for="username">아이디</label>
				<input type="text" id="userId" name="userId" placeholder="아이디를 입력하세요" style="width: 380px;">
			</div>
	   
			<div class="form-group">
				<label for="password">비밀번호</label>
				<input type="password" id="userPwd" name="userPwd" placeholder="비밀번호를 입력하세요" style="width: 380px;">
			</div>
	   
			<div class="btn-group">
				<button type="button" onclick="login()">로그인</button>
				<button type="button" onclick="member()">회원가입</button>
				<input type="hidden" name="key">
				<input type="hidden" name="userName">
				<input type="hidden" name="boardNum" value="-1">
				<button type="button" onclick="findInfo()">정보찾기</button>
			</div>
		</div>
	</form>

</body>
</html>

