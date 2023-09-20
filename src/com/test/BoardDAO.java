/*=====================
 	BoardDAO.java
 ======================*/

package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BoardDAO
{
	// 주요 속성 구성
	private Connection conn;
	
	// 생성자 정의
	public BoardDAO() throws ClassNotFoundException, SQLException
	{
		this.conn = DBConn.getConnection();
	}
	
	// 게시물 작성 → 데이터 입력
	public int insertData(BoardDTO dto)
	{
		int result =0;
		PreparedStatement pstmt = null;
		String sql ="";
		
		try
		{
			
			sql="INSERT INTO TBL_BOARD(NUM,CONTENT,CREATED,SID) VALUES(BOARD_SEQ.NEXTVAL,?,SYSDATE,?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, Integer.parseInt(dto.getSid()));
			
			result=pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}// end insertData(BoardDTO dto)
	
	
	// DB 레코드의 갯수를 가져오는 메소드 정의 (지금은 전체 갯수 조회)
	// → 추 후 ... 검색 기능을 작업하게 되면... 수정하게 될 메소드 (검색 대상 갯수 조회)
	
	public int getDataCount()
	{
		int result =0;
		
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			stmt = conn.createStatement();
			sql ="SELECT COUNT(*) AS COUNT FROM TBL_BOARD";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int getDataCount(int sid)
	{
		int result =0;
		
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			stmt = conn.createStatement();
			sql ="SELECT COUNT(*) AS COUNT FROM TBL_BOARD WHERE SID = " + String.valueOf(sid);
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				result = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}

	
	// 특정 게시물의 내용을 읽어오는 메소드 정의
	public List<BoardDTO> getReadData(int start, int end)
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql ="";
		
		try
		{
			sql="SELECT NUM, NIKNAME, CONTENT, CREATED"
		               + " FROM ( SELECT ROWNUM RNUM, DATA.*"
		               + " FROM ( SELECT B.NUM, M.NIKNAME, B.CONTENT, TO_CHAR(B.CREATED, 'YYYY-MM-DD') AS CREATED"
		               + " FROM TBL_BOARD B, TBL_MEMBER M WHERE B.SID = M.SID ORDER BY B.NUM DESC ) DATA )"
		               + " WHERE RNUM>=? AND RNUM<=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();	
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt(1));
				dto.setNikName(rs.getString(2));
				dto.setContent(rs.getString(3));
				dto.setCreated(rs.getString(4));
				
				result.add(dto);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 특정 게시물의 내용을 읽어오는 메소드 정의
	public List<BoardDTO> getReadData(int start, int end, int sid)
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql ="";
		
		try
		{
			sql="SELECT NUM, NIKNAME, CONTENT, CREATED"
		               + " FROM ( SELECT ROWNUM RNUM, DATA.*"
		               + " FROM ( SELECT B.NUM, M.NIKNAME, B.CONTENT, TO_CHAR(B.CREATED, 'YYYY-MM-DD') AS CREATED"
		               + " FROM TBL_BOARD B, TBL_MEMBER M WHERE B.SID = M.SID AND B.SID = ? ORDER BY B.NUM DESC ) DATA )"
		               + " WHERE RNUM>=? AND RNUM<=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sid);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();	
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt(1));
				dto.setNikName(rs.getString(2));
				dto.setContent(rs.getString(3));
				dto.setCreated(rs.getString(4));
				
				result.add(dto);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 특정 게시물을 삭제하는 기능의 메소드
	public int deleteData(int num)
	{
		int result =0;
		PreparedStatement pstmt = null;
		String sql ="";
		
		try
		{
			sql ="DELETE FROM TBL_LIKE WHERE NUM =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			result = pstmt.executeUpdate();
			
			sql ="DELETE FROM TBL_BOARD WHERE NUM =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 특정 게시물을 수정하는 기능의 메소드
	public int updateData(BoardDTO dto)
	{
		int result =0;
		PreparedStatement pstmt = null;
		String sql ="";
		try
		{
			sql = "UPDATE TBL_BOARD SET CONTENT=? WHERE NUM =?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getNum());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return result;
	}
	// 베스트3 방명록 리스트 조회 
	public List<BoardDTO> getBestlists()
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		String sql ="";
		
		try
		{
			sql="SELECT B.NUM,M.NIKNAME, B.CONTENT, TO_CHAR(B.CREATED,'YYYY-MM-DD')" + 
					" FROM TBL_BOARD B,"+
					" (SELECT NUM, COUNT, RANK" + 
					" FROM" + 
					" (SELECT NUM, COUNT(NUM) AS COUNT, DENSE_RANK() OVER(ORDER BY COUNT(NUM) DESC) AS RANK" + 
					" FROM TBL_LIKE" + 
					" GROUP BY NUM" + 
					" ORDER BY 2 DESC" + 
					" )T " + 
					" WHERE T.RANK <4" + 
					" ORDER BY 3) A" + 
					" ,TBL_MEMBER M" + 
					" WHERE B.NUM= A.NUM" + 
					" AND B.SID=M.SID";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();	
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt(1));
				dto.setNikName(rs.getString(2));
				dto.setContent(rs.getString(3));
				dto.setCreated(rs.getString(4));
				
				result.add(dto);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
}
