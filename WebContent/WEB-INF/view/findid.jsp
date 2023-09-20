<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
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

	function where(i)
	{
		var f = document.myForm;
		
		if (i == "1")
		{
			f.action = "syb";
			f.submit();
		}
		else if (i == "2")
		{
			f.key.value="findpw";
			
			f.action = "boardConn";
			f.submit();
		}
	}
	
	function findId()
	{
		var f = document.myForm;
		
		if (f.userName.value=="")
		{
			alert("닉네임을 입력해주세요.");
			f.userName.focus();
			return;
		}
		
		f.key.value = "checkid";
		
		f.action = "boardConn";
		f.submit();
	}

</script>

</head>
<body>

<form name="myForm" method="post">
	<div class="logo">
           <img src="images/loginlogo.png">
	</div>
	<div class="container">
	    <div class="form-container">
	        <h2>아이디 찾기</h2>
	        
	        <div class="form-group">
				<label for="userName">닉네임</label>
				<input type="text" id="userName" name="userName" placeholder="닉네임을 입력하세요" style="width: 380px;">
			</div>

            <div class="btn-group">
                <button type="button" class="btn" onclick="findId()">아이디 찾기</button>      
                <button type="button" class="btn" onclick="where(2)">비밀번호 찾기</button>
                <button type="button" class="btn" onclick="where(1)">로그인</button>
                <input type="hidden" name="key">
            </div>

	    </div>
	</div>

</form>

</body>
</html>