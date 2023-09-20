<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입 축하 페이지</title>
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


</head>
<body>
  <div class="container" style="text-align: center;">
    <h2>회원가입 완료</h2>
    <p><strong>${param.userName }<span class="nickname" id="nickname"></span></strong>님의 회원가입을 진심으로 축하합니다!</p>
	
	<form action="syb" method="post">
	    <div class="btn-group">
	      <button type="submit">로그인 창으로 넘어가기</button>
	    </div>
    </form>

  
  </div>
</body>
</html>

