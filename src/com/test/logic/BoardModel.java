package com.test.logic;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.BoardDAO;
import com.test.BoardDTO;
import com.test.LikeDAO;
import com.test.MemberDAO;
import com.test.MemberDTO;
import com.util.DBConn;


public class BoardModel
{
	HttpSession session; 
	
	public String loginCheck(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		String id = "";
		String pwd = "";
		
		// 요청객체의 세션 가져오기 ★★★★★★★
		session = request.getSession();
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			MemberDAO dao = new MemberDAO();
			
			// 입력받은 id, pwd 가져오기
			id = request.getParameter("userId");
			pwd = request.getParameter("userPwd");
			
			// 아이디하고 비밀번호가 존재하고 일치할 때 1을 반환하는 기능의 메소드 호출
			int check = dao.login(id, pwd);
			
			// 아이디하고 비밀번호가 존재하고 일치할 때
			// session에 id,pwd,sid를 session 에 setAttribute
			if (check==1)
			{
				session.setAttribute("userId", id);
				session.setAttribute("userPwd", pwd);
				session.setAttribute("sid", dao.getSid(id, pwd));
				
				// 방명록 메인 화면으로 이동
				result = "board.jsp";
			}
			// 로그인이 안되었을 때 주소 error 속성에 값 넘기기 ★★★★★
			// - 로그인 실패 알림 설정
			else
			{
				result = "login.jsp?error=true";
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				DBConn.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
		}
		
		return result;
	}
	
	public String dupliId(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int tmp = 0;
		String check = "";
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			MemberDAO dao = new MemberDAO();

			// 입력한 id 가 존재하는 id 인지 확인 ( 1 → 존재)
			tmp = dao.selectId(request.getParameter("userId"));
			check = request.getParameter("check");
			
			// 회원가입 시 중복확인
			if (check.equals("insert"))
			{
				if (tmp == 1)
					result = "member.jsp?dupli=id";
				else
					result = "member.jsp?dupli=noneId&id="+request.getParameter("userId");
			}
			// 회원정보 수정 시 중복확인
			else if (check.equals("modify"))
			{
				if (tmp == 1)
					result = "modify.jsp?dupli=id";
				else
					result = "modify.jsp?dupli=noneId&id="+request.getParameter("userId");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
		return result;
	}
	
	public String dupliName(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int tmp = 0;
		String check = "";
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			MemberDAO dao = new MemberDAO();
			
			// 입력한 닉네임이 존재하는 닉네임인지 확인
			tmp = dao.selectNikName(request.getParameter("userName"));
			check = request.getParameter("check");
			
			// 회원가입 시 중복확인
			if (check.equals("insert"))
			{
				if (tmp == 1)
					result = "member.jsp?dupli=name";
				else
					result = "member.jsp?dupli=noneName&id="+request.getParameter("userId")+"name="+request.getParameter("userName");
			}
			// 회원정보 수정 시 중복확인
			else if (check.equals("modify"))
			{
				if (tmp == 1)
					result = "modify.jsp?dupli=name&id="+request.getParameter("userId");
				else
					result = "modify.jsp?dupli=noneName&id="+request.getParameter("userId")+"name="+request.getParameter("userName");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
		return result;
	}
	
	public String memberInsert(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int check = 0;
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			
			// 입력한 id, pwd, nikname를 DTO 에 set
			dto.setId(request.getParameter("userId"));
			dto.setPwd(request.getParameter("userPwd"));
			dto.setNikName(request.getParameter("userName"));
			
			check = dao.insertMember(dto);
			
			// 테이블에 입력 성공 시 회원가입 축하 화면 요청하게끔 함
			if (check==1)
			{
				result = "welcome.jsp";
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return result;
	}
	
	public String memberModify(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int check = 0;
		HttpSession session = request.getSession();
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			
			dto.setSid(String.valueOf(session.getAttribute("sid")));
			dto.setId(request.getParameter("userId"));
			if (request.getParameter("newPwd").equals(""))
				dto.setPwd(request.getParameter("userPwd"));
			else
				dto.setPwd(request.getParameter("newPwd"));
			dto.setNikName(request.getParameter("userName"));
			
			check = dao.modifyMember(dto);
			
			if (check==1)
			{
				result = "board.jsp";
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return result;
	}
	
	
	public String boardInsert(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int check = 0;
		session = request.getSession();
		
		try
		{
			// 컨트롤러에서 가져온 "TOKEN_CHECK" 에 대해서 분기
			// "TRUE" 라면 dao.insertData(dto); 호출 후 정상적인 등록 시작
			if ("TRUE".equals(request.getAttribute("TOKEN_CHECK")))
			{
				request.setCharacterEncoding("UTF-8");
				
				BoardDAO dao = new BoardDAO();
				BoardDTO dto = new BoardDTO();
				
				dto.setSid(String.valueOf(session.getAttribute("sid")));
				dto.setContent(request.getParameter("content"));
				
				
				check = dao.insertData(dto);
				
				if (check==1)
				{
					result = "board.jsp?pageNum=1";
				}
			}
			else
			{
				// 같지 않다면 이미 한번 요청되었음
				result="board.jsp";
			}
			
			
		} 
		
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		return result;
	}
	
	public String boardModify(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int check = 0;
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			BoardDAO dao = new BoardDAO();
			BoardDTO dto = new BoardDTO();
			
			dto.setNum(Integer.parseInt(request.getParameter("boardNum")));
			dto.setContent(request.getParameter("updateContent"));
			
			
			check = dao.updateData(dto);
			
			if (check==1)
			{
				result = "board.jsp?pageNum="+request.getParameter("page")+"&boardNum=-1";
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
		return result;
	}
	
	public String boardDelete(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		int check = 0;
		
		try
		{
			request.setCharacterEncoding("UTF-8");
			
			BoardDAO dao = new BoardDAO();
			
			int num = Integer.parseInt(request.getParameter("boardNum"));
			
			check = dao.deleteData(num);
			
			if (check==1)
			{
				result = "board.jsp?pageNum="+request.getParameter("page")+"&boardNum=-1";
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
		
		return result;
	}
	
	public String likeCheck(HttpServletRequest request, HttpServletResponse response)
	   {
	      String result = "";
	      int check = 0;
	      session = request.getSession();
	      try
	      {
	         String num =request.getParameter("boardNum");
	         Integer sid = (Integer)session.getAttribute("sid");
	         LikeDAO dao = new LikeDAO();
	         
	         check = dao.likeCheck(Integer.parseInt(num), sid);
	         
	         
	         if (check==1)
	            // 삭제
	        	 dao.likeDel(Integer.parseInt(num), sid);
	         else
	            // 입력
	            dao.likeAdd(Integer.parseInt(num), sid);
	         
	         result = "board.jsp?boardNum=-1&pageNum="+request.getParameter("page");
	         
	      } catch (Exception e)
	      {
	         System.out.println(e.toString());
	      }
	      finally
	      {
	         try
	         {
	            DBConn.close();
	         } catch (Exception e)
	         {
	            System.out.println(e.toString());
	         }
	      }
	      
	      return result;
	   }
	
	public String findId(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		session = request.getSession();
		try
		{
			String userName =request.getParameter("userName");
			MemberDAO dao = new MemberDAO();
	 
			String userId = dao.findId(userName);
			
			if (userId.equals(""))
				result = "getinfo.jsp?check=errorId";
			else
				result = "getinfo.jsp?check=findid&userId="+userId;
	         
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	      
		return result;
	}
	
	
	public String findPwd(HttpServletRequest request, HttpServletResponse response)
	{
		String result = "";
		session = request.getSession();
		try
		{
			String userId =request.getParameter("userId");
			String userName =request.getParameter("userName");
			MemberDAO dao = new MemberDAO();
	 
			String userPwd = dao.findPwd(userName, userId);
			
			if (userPwd.equals(""))
				result = "getinfo.jsp?check=errorPwd";
			else
				result = "getinfo.jsp?check=findpwd&userPwd="+userPwd;
	         
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				DBConn.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	      
		return result;
	}
	
	
	
}
