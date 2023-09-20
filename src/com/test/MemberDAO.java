package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MemberDAO
{
	// 주요 속성 구성
	private Connection conn;
		
	// 생성자 정의
	public MemberDAO() throws ClassNotFoundException, SQLException
	{
		this.conn = DBConn.getConnection();
	}
	
	
	// login 여부 확인 메소드 정의
	public int login(String id, String pwd)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER WHERE ID = ? AND PWD = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	
	public int getSid(String id, String pwd)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			sql = "SELECT SID FROM TBL_MEMBER WHERE ID = ? AND PWD = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
				result = rs.getInt("SID");
			
			rs.close();
			pstmt.close();
			
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// id 존재 여부를 확인하는 메소드 정의
	public int selectId(String id)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER WHERE ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
		
	}
	
	// 닉네임 존재 여부를 확인하는 메소드 정의
	public int selectNikName(String nikName)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try
		{
			sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER WHERE NIKNAME = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, nikName);
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	
	// 회원 정보 insert 메소드 정의
	public int insertMember(MemberDTO dto)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try 
		{
			sql = "INSERT INTO TBL_MEMBER(SID, ID, NIKNAME, PWD, CREATED) VALUES(MEMBER_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getNikName());
			pstmt.setString(3, dto.getPwd());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 회원 정보 update 메소드 정의
	public int modifyMember(MemberDTO dto)
	{
		int result = 0;			
		
		PreparedStatement pstmt = null;
		String sql = "";
		
		try
		{
			sql = "UPDATE TBL_MEMBER SET ID = ?, PWD = ?, NIKNAME = ?"
					+ " WHERE SID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getNikName());
			pstmt.setInt(4, Integer.parseInt(dto.getSid()));
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return result;
		
	}
	
	
	// 회원 정보 delete 메소드 정의 
	public int removeMember(int sid)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try
		{
			sql = "DELETE FROM TBL_BOARD WHERE NUM = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, sid);
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
			
		} catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	
	// 추가 메소드 정의
	
	// 로그인되어 있는 회원의 이름을 가져오는 메소드
	public String getUserName(String id)
	{
		String result = "";
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT NIKNAME FROM TBL_MEMBER WHERE ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getString("NIKNAME");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 닉네임으로 아이디 찾아오기
	public String findId(String userName)
	{
		String result = "";
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT ID FROM TBL_MEMBER WHERE NIKNAME = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getString("ID");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	//아이디와 닉네임으로 비밀번호 찾아오기
	public String findPwd(String userName, String id)
	{
		String result = "";
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT PWD FROM TBL_MEMBER WHERE NIKNAME = ? AND ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			pstmt.setString(2, id);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getString("PWD");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
		

}
