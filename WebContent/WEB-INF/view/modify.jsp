<%@page import="com.test.MemberDTO"%>
<%@page import="com.test.MemberDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String dupli = request.getParameter("dupli");
	String id = (String)session.getAttribute("userId");
	String pwd = (String)session.getAttribute("userPwd");
	
	String name = "";
	
	MemberDAO dao = null;
	MemberDTO dto = null;
	
	try
	{
		dao = new MemberDAO();
		dto = new MemberDTO();
		
		name = dao.getUserName(id);
		
	}catch (Exception e)
	{
		System.out.println(e.toString());
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원수정 페이지</title>

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

<link rel="stylesheet" type="text/css" href="css/main.css">

<script type="text/javascript">

	function check()
	{
		var f = document.myForm;
		
		if ("<%=dupli %>" == "id")
		{
			alert("아이디가 중복입니다.");
			f.userId.focus();			
		}
		else if("<%=dupli %>" == "name")
		{
			alert("닉네임이 중복입니다.");
			f.userId.value = "<%=id%>";
			f.userName.focus();
		}
		else if ("<%=dupli %>" == "noneId")
		{
			alert("사용가능한 아이디 입니다.")
			
			f.userId.value = "<%=request.getParameter("userId")%>";
			f.userName.focus();
		}
		else if ("<%=dupli %>" == "noneName")
		{
			alert("사용가능한 닉네임 입니다.")
			f.userId.value = "<%=request.getParameter("userId")%>";
			f.userName.value = "<%=request.getParameter("userName")%>";
			f.userPwd.focus();
		}
		else
		{
			f.userId.value = "<%=id %>";
			f.userName.value = "<%=name %>";
		}
	}

	function modify()
	{
		var f = document.myForm;
		
		if (f.userId.value == "")
		{
			alert("아이디를 입력하세요.");
			f.userId.focus();
			return;
		}
		
		if (f.userName.value == "")
		{
			alert("닉네임을 입력하세요.");
			f.userName.focus();
			return;
		}
		
		if (f.userPwd.value == "")
		{
			alert("비밀번호를 입력하세요.");
			f.userPwd.focus();
			return;
		}
		
		if ("<%=pwd %>"!=f.userPwd.value)
		{
			alert("기존 비밀번호가 올바르지 않습니다/");
			f.userPwd.value = "";
			f.userPwd.focus();
			return;
		}
		
		if (f.newPwd.value!=f.newCheckPwd.value)
		{
			alert("비밀번호가 동일하지 않습니다.");
			f.newPwd.value = "";
			f.newCheckPwd.value = "";
			f.newPwd.focus();
			return;
		}
		
		f.key.value = "modify_ok";
		
		f.action = "boardConn";
		
		f.submit();
	}
	
	function dupli(i)
	{
		var f = document.myForm;
		
		if (i == 1)
		{
			if (f.userId.value == "")
			{
				alert("아이디를 입력하세요.");
				f.userId.focus();
				return;
			}
			else if ("<%=id %>"==f.userId.value)
			{
				alert("변경할 아이디를 입력하세요.");
				return;
			}
			
			f.key.value = "dupliId";
		}
		else if (i == 2)
		{
			if (f.userName.value=="")
			{
				alert("닉네임을 입력하세요.");
				f.userName.focus();
				return;
			}
			else if ("<%=name %>"==f.userName.value)
			{
				alert("변경할 이름을 입력하세요.");
				return;
			}
			
			f.key.value = "dupliName";
			
		}
		
		f.action ="boardConn";
		
		f.submit();
	}
	
	function board()
	{
		var f = document.myForm;
		
		f.page.value = "1";
		
		f.action = "board";
		f.submit();
	}

</script>

</head>
<body  onload="check()">

<form name="myForm" class="container" method="post">
  
    <h2>정보수정</h2>
    
    <div class="form-group">
      <label for="username">아이디</label>
      <input type="text" id="userId" name="userId" style="width: 280px;" value="<%=id %>">
      <button type="button" onclick="dupli(1)">중복 확인</button>
    </div>
    
     <div class="form-group">
      <label for="username">닉네임</label>
      <input type="text" id="userName" name="userName" style="width: 280px;" value="<%=name %>">
      <button type="button" onclick="dupli(2)">중복 확인</button>
    </div>
    
    <div class="form-group">
      <label for="password">기존 비밀번호</label>
      <input type="password" id="userPwd" name="userPwd" style="width: 380px;"
       placeholder="기존 비밀번호를 입력하세요">
    </div>
    
    <div class="form-group">
    	<span style="font-size: 8pt; color: red">※ 비밀번호 변경 원하지 않는 경우 해당 항목 생략 가능 ※</span>
		<label for="password">새로운 비밀번호</label>
		<input type="password" id="newPwd" name="newPwd" style="width: 380px;"
		placeholder="변경하실 비밀번호를 입력하세요">
	</div>
    
    <div class="form-group">
		<span style="font-size: 8pt; color: red">※ 비밀번호 변경 원하지 않는 경우 해당 항목 생략 가능 ※</span>
		<label for="password">새로운 비밀번호 확인</label>
		<input type="password" id="newCheckPwd" name="newCheckPwd" style="width: 380px;"
		placeholder="변경하실 비밀번호를 한번 더 입력하세요">
	</div>
    
    <div class="btn-group">
      <button type="button" onclick="modify()">수정등록</button>
      <button type="button" onclick="board()">뒤로가기</button>
    </div>
    
    <input type="hidden" name="key">
    <input type="hidden" name="page">
    <input type="hidden" name="check" value="modify">
    <input type="hidden" name="boardNum" value="-1">
    <!-- <input type="hidden" name="check" value=""> -->
        
</form>

</body>
</html>