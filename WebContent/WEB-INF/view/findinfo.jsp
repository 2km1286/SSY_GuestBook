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
	
	.btn-group button 
	{
		width: 250px;
		padding: 10px 20px;
		border: none;
		border-radius: 5px;
		background-color: #4CAF50;
		color: white;
		cursor: pointer;
		font-size: 16px;
		margin-right: 10px;
	}
    
</style>

<script type="text/javascript">

	function where(i)
	{
		var f = document.myForm;
		
		if (i=="1")
		{
			f.action = "syb";
			f.submit();
			return;
		}
		else if (i=="2")
			f.key.value = "findid";
		else if (i=="3")
			f.key.value = "findpw";
		
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
   		<h2>회원정보 찾기</h2>
    	<div class="btn-group">
        	<button type="button" name="id" class="btn" onclick="where(2)">아이디 찾기</button><br>
        </div>
        <div class="btn-group">
        	<button type="button" name="pw" class="btn" onclick="where(3)">비밀번호 찾기</button><br>
        </div>
        <div class="btn-group">
        	<button type="button" name="login" class="btn" onclick="where(1)">로그인 하기</button><br>
			<input type="hidden" name="key">    	
    	</div>
	</div>
</form>


</body>
</html>