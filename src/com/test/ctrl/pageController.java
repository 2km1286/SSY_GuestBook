package com.test.ctrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class pageController extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// 서블릿 관련 코딩
		
		// View 에 대한 분기 → 어떤 요청인지 판단
		/*
		 * String key = request.getParameter("key");
		 * 
		 * BoardModel model = new BoardModel();
		 * 
		 * String view = "";
		 * 
		 * if (key.equals("login")) view = model.loginCheck(request, response); else if
		 * (key.equals("member")) view = "member.jsp"; else if (key.equals("dupliId"))
		 * view = model.dupliId(request, response); else if (key.equals("dupliName"))
		 * view = model.dupliName(request, response); else if (key.equals("insert"))
		 * view = model.memberInsert(request, response);
		 */
		String pageNum = request.getParameter("page");

		String key = request.getParameter("key");
		  
		if(key.equals("1"))
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/myboard.jsp?&pageNum="+pageNum);
			dispatcher.forward(request, response);
		}
		else   
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/board.jsp?pageNum="+pageNum);
			dispatcher.forward(request, response);
		}
		
		
	}
}
