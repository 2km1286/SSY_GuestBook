<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	//중복 확인 시 해당 id 또는 닉네임 존재 여부 알 수 있는 값 담김
	String dupli = request.getParameter("dupli");
	String name = request.getParameter("userName");
	String id = request.getParameter("userId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
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
		var f = document.myForm;
		
		// 입력한 아이디가 중복일 때
		if ("<%=dupli %>" == "id")
		{
			alert("아이디가 중복입니다.");
			f.userId.focus();			
		}
		// 입력한 닉네임 중복일 때
		else if("<%=dupli %>" == "name")
		{
			alert("닉네임이 중복입니다.");
			f.userId.value = "<%=id%>";
			f.userName.focus();
		}
		// 입력한 아이디가 사용 가능할 때
		else if ("<%=dupli %>" == "noneId")
		{
			alert("사용가능한 아이디 입니다.")
			/* f.check.value += "idOk " */
			f.userId.value = "<%=id%>";
			f.userName.focus();
		}
		// 입력한 닉네임 사용 가능할 때
		else if ("<%=dupli %>" == "noneName")
		{
			alert("사용가능한 닉네임 입니다.")
			/* f.check.value +="nameOk" */
			f.userId.value = "<%=id%>";
			f.userName.value = "<%=name%>";
			f.userPwd.focus();
		}
		// 중복확인 하지 않았을 때 
		else
		{
			f.userId.value = "<%=id%>";
			f.userName.value = "<%=name%>";
		}
	}

	function insert()
	{
		var f = document.myForm;
		
		// 아이디 입력 안했을 때
		if (f.userId.value == "")
		{
			alert("아이디를 입력하세요.");
			f.userId.focus();
			return;
		}
		
		// 닉네임 입력 안했을 때
		if (f.userName.value == "")
		{
			alert("닉네임을 입력하세요.");
			f.userName.focus();
			return;
		}
		
		// 비밀번호 입력 안했을 때
		if (f.userPwd.value == "")
		{
			alert("비밀번호를 입력하세요.");
			f.userPwd.focus();
			return;
		}
		
		// 확인 비밀번호 입력 안했을 때
		if (f.checkPwd.value == "")
		{
			alert("확인 비밀번호를 입력하세요.");
			f.checkPwd.focus();
			return;
		}
		
		// 비밀번호와 확인 비밀번호가 일치하지 않을 때
		if (f.checkPwd.value != f.userPwd.value)
		{
			alert("비밀번호가 동일하지 않습니다.");
			
			// 비밀번호 및 확인비밀번호 값 지우기
			f.userPwd.value = "";
			f.checkPwd.value = "";
			f.userPwd.focus();
			return;
		}
		
		// key 속성값으로 insert 넘기기
		f.key.value = "insert";
		
		f.action = "boardConn";
		
		f.submit();
	}
	
	function dupli(i) // 중복확인 함수
	{
		var f = document.myForm;
		
		if (i == 1) // 아이디 중복확인
		{
			// 입력 안했을 때 입력 경고창 띄움
			if (f.userId.value == "")
			{
				alert("아이디를 입력하세요.");
				f.userId.focus();
				return;
			}
			
			// key 속성 값 넣기
			f.key.value = "dupliId";
		}
		else if (i == 2)
		{
			if (f.userName.value == "")
			{
				alert("닉네임을 입력하세요.");
				f.userName.focus();
				return;
			}
			
			f.key.value = "dupliName";
			
		}
		
		f.action ="boardConn";
		
		f.submit();
	}
	
	function login()
	{
		var f = document.myForm;
		
		f.action = "syb";
		f.submit();
	}
	
</script>

</head>
<body  onload="check()">

<form name="myForm" class="container" method="post">
  
    <h2>회원가입</h2>
    
    <div class="form-group">
      <label for="username">아이디</label>
      <input type="text" id="userId" name="userId" style="width: 280px;"
       placeholder="사용하실 아이디를 입력하세요" onchange="checkId()"> <button type="button" onclick="dupli(1)">중복 확인</button>
    </div>
    
     <div class="form-group">
      <label for="username">닉네임</label>
      <input type="text" id="userName" name="userName" style="width: 280px;"
       placeholder="사용하실 닉네임을 입력하세요"> <button type="button" onclick="dupli(2)">중복 확인</button>
       <input type="hidden" name="nameCheck">
    </div>
    
    <div class="form-group">
      <label for="password">비밀번호</label>
      <input type="password" id="userPwd" name="userPwd" style="width: 380px;"
       placeholder="사용하실 비밀번호를 입력하세요">
    </div>
    
    <div class="form-group">
      <label for="password">비밀번호 확인</label>
      <input type="password" id="checkPwd" name="checkPwd" style="width: 380px;"
       placeholder="사용하실 비밀번호를 한번 더 입력하세요">
    </div>
    
    <div class="btn-group">
      <button type="button" onclick="insert()">가입요청</button>
      <button type="button" onclick="login()">뒤로가기</button>
    </div>
    
    <input type="hidden" name="key">
    <input type="hidden" name="check" value="insert">
    <!-- <input type="hidden" name="check" value=""> -->
        
</form>

</body>
</html>