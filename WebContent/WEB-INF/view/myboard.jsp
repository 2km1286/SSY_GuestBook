<%@page import="com.test.LikeDAO"%>
<%@page import="com.test.logic.BoardModel"%>
<%@page import="com.util.DBConn"%>
<%@page import="com.test.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.util.MyUtil"%>
<%@page import="com.test.BoardDAO"%>
<%@page import="com.test.MemberDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session ='true'%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<%
	String userId = (String)session.getAttribute("userId");
	String userPwd = (String)session.getAttribute("userPwd");
	int sid = Integer.parseInt(String.valueOf(session.getAttribute("sid")));
/* 	String update = request.getParameter("update"); */

	int likeCount = 0;
	int likeAdd = 0;
	
	// 이전 페이지(?)로부터 넘어온 게시물 번호 수신
	String strNum = request.getParameter("num");
	int num=0;
	if (strNum != null)
		num = Integer.parseInt(strNum);
	
	// 이전 페이지(?)로부터 넘어온 페이지 번호 수신
	String pageNum = request.getParameter("pageNum");
	int currentPage = 1;
	if (pageNum!=null)
		currentPage = Integer.parseInt(pageNum);
	
	MemberDAO memberDao = null;
	BoardDAO boardDao = null;
	LikeDAO likeDao = null;
	MyUtil myUtil = null;
	
	int dataCount = 0;
	List<BoardDTO> lists = null;
	List<String> memberLists = null;
	String pageIndexList = "";
	String userName = "";
	
	try
	{
		memberDao = new MemberDAO();
		boardDao = new BoardDAO();
		likeDao = new LikeDAO();
		myUtil = new MyUtil();
		
		// 전체 데이터 갯수 구하기
		dataCount = boardDao.getDataCount(sid);
		
		// 전체 페이지를 기준으로 총 페이지 수 계산
		int numPerPage = 7;					//-- 한 페이지에 표시할 데이터 갯수
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		// 전체 페이지 수 보다 표시할 페이지가 큰 경우
		// 표시할 페이지를 전체 페이지로 처리
		// → 데이터를 삭제해서 페이지가 줄어들었을 경우...
		if (currentPage > totalPage)
			currentPage = totalPage;
		
		// 데이터베이스에서 가져올 시작과 끝 위치
		int start = (currentPage-1) * numPerPage + 1;
		int end = currentPage * numPerPage;
		
		// 실제 리스트 가져오기
		lists = boardDao.getReadData(start, end, sid);

		
		pageIndexList = myUtil.pageIndexList(currentPage, totalPage);
		
		// 로그인 되어있는 회원의 닉네임 가지고 오기
		userName = memberDao.getUserName(userId);
		
	}catch (Exception e)
	{
		System.out.println(e.toString());
	}
	finally
	{
		try
		{
			DBConn.close();
			
		}catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<title>방명록</title>
<link rel="stylesheet" type="text/css" href="css/afterlogin.css">
<link rel="stylesheet" type="text/css" href="css/guestbook.css">


<script type="text/javascript">

	function keyReset()
	{
		var f = document.myForm;
		
		f.key.value="";
		f.content.value="";
	}
	
	function page(i)
   {
      var f = document.myForm;
      
      f.page.value = i;
      f.key.value = 1;
      
      f.action="board";
      f.submit();
   }
	
	function firstPage()
	{
		var f = document.myForm;
		
		f.page.value = 1;
		
		f.action="board";
		f.submit();
	}
	
	function modify()
	{
		var f = document.myForm;
		
		f.key.value = "modify";
		
		f.action = "boardConn";
		f.submit();
	}
	
	function logOut()
	{
		var f = document.myForm;
		
		f.action = "syb";
		f.submit();
	}
	
	function boardInsert()
	{
		var f = document.myForm;
		
		f.key.value = "boardinsert";
		f.page.value="1";
		
		f.action="boardConn";
		f.submit();
	}
	
	function boardUpdate(i)
	{
		var f = document.myForm;
		
		f.boardNum.value = i;
		f.page.value="<%=currentPage %>";
		
		f.action="board";
		f.submit();
	}
	
	function boardModify(i)
	{
		var f = document.myForm;
		
		f.page.value="<%=currentPage %>";
		f.boardNum.value = i;
		
		f.key.value = "board";
		
		f.action="boardConn";
		f.submit();
	}
	
	function boardDelete(i)
	{
		var f = document.myForm;
		
		if(!confirm('삭제하시면 복구할수 없습니다. \n 정말로 삭제하시겠습니까??')){
	        return;
	    }
		else
		{
			f.boardNum.value = i;
			f.page.value="<%=currentPage %>";
			
			f.key.value = "delete";
			
			f.action="boardConn";
			f.submit();
		}
		
	}
	
	function like(i)
	{
	   var f = document.myForm;
	   
	   f.key.value = "like";
	   f.page.value = "<%=currentPage %>";
	   f.boardNum.value = i;
	   
	   f.action = "boardConn";
	   f.submit();
	}
	
	function myPage()
	{
		var f = document.myForm;
		
		f.key.value = "mypage";
		
		
		f.action = "boardConn";
		f.submit();
		
	}

</script>


</head>
<body onclick="keyReset()">
<form name="myForm" method="post">
	<header>
		<div class="logo">
			<img src="images/WWW.png" onclick="firstPage()">
		</div>
		<div class="user-info">
			<span style="font-weight: bold; font-style: italic; font-family: 맑은 고딕; font-size: 15pt;" ><%=userName %></span>
			<span style="font-style: italic;">님 안녕하세요</span>
			<button type="button" onclick="firstPage()">첫페이지</button>
			<button type="button" onclick="modify()">정보수정</button>
			<button type="button" onclick="logOut()">로그아웃</button>
		</div>
	</header>
	<hr>
	
	<div class="ad">
  
    <marquee style="background-color: rgb(245, 252, 101);">
   	  📢  ★ EVENT ★ JAVA 개발자 훈련과정 참여 시 제네시스 G90 차량 무료 증정 !
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	  📢  ★ EVENT ★ Python 과정 면접 신청 시 면접 지원금 1,000,000원 당일 지급 !
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 	  📢  ★ EVENT ★ DBMS 개발자 훈련 과정 수료 시 축하금 30,000,000원 즉시 지급 !
    </marquee> 
   
    </div>
    
	<div class="imgLogo">
		<img src="images/gang.png" style="float: left; margin-right: 0;">
  		<img src="images/huhu.gif" style="display: inline-block; margin: 0;">
  		<img src="images/itit.png" style="float: left; margin-right: 0;">
	</div>
	
	
  	<%
	for (BoardDTO dto : lists)
	{
		if (dto.getNum() == Integer.parseInt(request.getParameter("boardNum"))){
	%>
		   <div class="guestbook_post">
		   <div class="guestbook_title">
		   <div class="guestbook_no"><%=dto.getNum() %></div>
		   <div class="guestbook_name"><%=dto.getNikName() %></div>
		   <div class="guestbook_date"><%=dto.getCreated() %></div>
		 <%
		 	try
		 	{
		 		likeDao = new LikeDAO();
	            memberLists = likeDao.getLikeList(dto.getNum());
	            likeCount = likeDao.likeCount(dto.getNum());
		 	}catch (Exception e)
		 	{
		 		System.out.println(e.toString());
		 	}
           
            if (memberLists.contains(String.valueOf(sid)))
            {
        %>   
                 <input type="button" class="btn" onclick="like(<%=dto.getNum()%>)" style="color: red;" value="♥ <%=likeCount %>">
        <%    }else {%>
                 <input type="button" class="btn" onclick="like(<%=dto.getNum()%>)" value="♡ <%=likeCount %>">
        <%    }%>
		   	<input type="button" class="btn" onclick="boardDelete(<%=dto.getNum() %>)" value="삭제">
		   <input type="button" class="btn" onclick="boardModify(<%=dto.getNum() %>)" value="확인">
		   </div>
		   <div class="guestbook_content">
		   <div class="guestbook_text">
		   
		   <textarea autofocus name="updateContent"
		   style="width: 1500px; height: 20px; padding: 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 4px;"
		   ><%=dto.getContent() %></textarea>
		   
		   </div>
		   </div>
		   </div>
	<%
		}else{
	%>
	   		<div class="guestbook_post">
	   		<div class="guestbook_title">
	   		<div class="guestbook_no"><%=dto.getNum() %></div>
	   		<div class="guestbook_name"><%=dto.getNikName() %></div>
	   		<div class="guestbook_date"><%=dto.getCreated() %></div>
	   	<%
		 	try
		 	{
		 		likeDao = new LikeDAO();
	            memberLists = likeDao.getLikeList(dto.getNum());
	            likeCount = likeDao.likeCount(dto.getNum());
		 	}catch (Exception e)
		 	{
		 		System.out.println(e.toString());
		 	}
           
            if (memberLists.contains(String.valueOf(sid)))
            {
        %>   
                 <input type="button" class="btn" onclick="like(<%=dto.getNum()%>)" style="color: red;" value="♥ <%=likeCount %>">
        <%    }else {%>
                 <input type="button" class="btn" onclick="like(<%=dto.getNum()%>)" value="♡ <%=likeCount %>">
        <%    }%>
	   		<input type="button" class="btn" onclick="boardDelete(<%=dto.getNum() %>)" value="삭제">
	   		<input type="button" class="btn" onclick="boardUpdate(<%=dto.getNum() %>)" value="수정">

	   		
	   		</div>
	   		<div class="guestbook_content">
	   		<div class="guestbook_text">
	   		<%=dto.getContent() %>
	   		</div>
	   		</div>
	   		</div>
	<%
		}
	}
	%>
	
  
	<div id="footer">
		<!-- <p>1 Prev 21 22 23 24 25 26 27 28 29 30 Next 52</p> -->
		<!-- <p>등록된 게시물이 존재하지 않습니다.</p> -->
		
		<p style="text-align: center;">
		<%if (dataCount!=0){ %>
			<%=pageIndexList %>
		<%}else{%>
			등록된 게시물이 존재하지 않습니다.
		<%}%>
		</p>
		<input type="hidden" name="page">
		<input type="hidden" name="key">
		<input type="hidden" name="boardNum" value="-1">
		
	</div><!-- footer -->
	
	<div class="end">
	<img src="images/endend.png" style="float: left; margin-right: 0;">
	</div>
	
</form>
</body>
</html>
