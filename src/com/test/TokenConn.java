package com.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TokenConn
{
	// TOKEN_KEY 에 "TOKEN_KEY" 대입하여 미리 토큰 값 선정 이후 이 변수로 편하게 뷰페이지 네임속성 대용 및 세션이름 대용 -> 통일하게 사용하기 위함
	private static final String TOKEN_KEY = "TOKEN_KEY";	
	
	public static void set(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		
		// 토큰이 될 토큰 생성 -> 현재 시간 밀리초로 선정 -> 버튼이 눌렸을 시점부터 무조건 다르게 나올 토큰
		String token = String.valueOf(System.currentTimeMillis());
		
		// 위 밀리초 토큰을 각각 request.setAttribute , session.setAttribute
		
		// 이 TokenConn 을 호출한 곳에서 쓰일 용도
		request.setAttribute("TOKEN_KEY", token);    //--> 첫번째 매개변수에 맨 위 변수 TOKEN_KEY 그대로 써도 됨 예시로 저렇게 해놈 밑에는 그대로 씀
		
		// 계속해서 쓰일 용도
		session.setAttribute(TOKEN_KEY, token);
	}
	
	// 뷰페이지에서 가져온 토큰과 비교할 메소드
	public static boolean isValid(HttpServletRequest request)
	{
		  HttpSession session = request.getSession();
		  
		  // 뷰 페이지 에서 name = "TOKEN_KEY"  파라미터 가져옴
		  String requestToken = request.getParameter(TOKEN_KEY);
		  
		  // 세션에서 "TOKEN_KEY" 이름의 token 을 가져옴
		  String sessionToken = (String)session.getAttribute(TOKEN_KEY);
		  if(requestToken == null || sessionToken == null) 
		  {
		   return false;
		  } 
		  else 
		  {
		   return requestToken.equals(sessionToken);
		  }
	}
}
