/*
 	boardController
 */

package com.test.ctrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.TokenConn;
import com.test.logic.BoardModel;

public class boardController extends HttpServlet
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
		request.setCharacterEncoding("UTF-8");
		
		// 서블릿 관련 코딩
		
		// View 에 대한 분기 → 어떤 요청인지 판단
		String key = request.getParameter("key");
		
		BoardModel model = new BoardModel();
		
		String view = "";	
		
		if (key.equals("login"))
			view = model.loginCheck(request, response);
		else if (key.equals("findinfo"))
			view = "findinfo.jsp";
		else if (key.equals("findid"))
			view = "findid.jsp";
		else if (key.equals("checkid"))
			view = model.findId(request, response);
		else if (key.equals("findpw"))
			view = "findpw.jsp";
		else if (key.equals("checkpwd"))
			view = model.findPwd(request, response);
		else if (key.equals("member"))
			view = "member.jsp?";
		else if (key.equals("dupliId"))
			view = model.dupliId(request, response);
		else if (key.equals("dupliName"))
			view = model.dupliName(request, response);
		else if (key.equals("insert"))
			view = model.memberInsert(request, response);
		else if (key.equals("modify"))
			view = "modify.jsp";
		else if (key.equals("modify_ok"))
			view = model.memberModify(request, response);
		
		// boardinsert 로 보내기전 요청한 뷰 페이지와 같은 지 확인
		else if (key.equals("boardinsert"))
		{
			// 등록 버튼 눌렀을 시 분기 시작 - TokenConn.isValid() 호출 
			// 같다면 본래 설계했던 boardinsert 로 request.setAttribute("TOKEN_CHECK", "TRUE"); 담아서 보냄
			// 같지 않다면(이미 밀리초 토큰이 만들어져 같지않음) request.setAttribute("TOKEN_CHECK", "FALSE");
			if (TokenConn.isValid(request))
			{
				TokenConn.set(request);
				request.setAttribute("TOKEN_CHECK", "TRUE");
				view = model.boardInsert(request, response);
			}
			else
			{
				request.setAttribute("TOKEN_CHECK", "FALSE");
				view = model.boardInsert(request, response);
			}
			
		}
		
		else if (key.equals("board"))
			view = model.boardModify(request, response);
		else if (key.equals("delete"))
			view = model.boardDelete(request, response);
		else if(key.equals("like"))
	         view = model.likeCheck(request, response);
		else if(key.equals("mypage"))
			view = "myboard.jsp";
			
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/"+view);
		dispatcher.forward(request, response);
		
		
	}
}