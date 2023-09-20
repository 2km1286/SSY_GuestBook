package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class LikeDAO
{
	// 주요 속성 구성
	private Connection conn;
		
	// 생성자 정의
	public LikeDAO() throws ClassNotFoundException, SQLException
	{
		this.conn = DBConn.getConnection();
	}	
	
	// 추가 메소드 정의
	
	// 해당 방명록의 좋아요 수 알아내기
	public int likeCount(int num)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT COUNT(*) AS COUNT FROM TBL_LIKE WHERE NUM = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 로그인한 유저가 해당 방명록에 좋아요 여부 확인 
	public int likeCheck(int num, int sid)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT COUNT(*) AS COUNT FROM TBL_LIKE WHERE NUM = ? AND SID =?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setInt(2, sid);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	// 해당 방명록의 좋아요 수 알아내기
	public int likeAdd(int num, int sid)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try
		{
			sql = "INSERT INTO TBL_LIKE(NUM,SID) VALUES(?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setInt(2, sid);
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// 해당 방명록의 좋아요 삭제
	public int likeDel(int num,int sid)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		
		try
		{
			sql = "DELETE FROM TBL_LIKE WHERE NUM =? AND SID =?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setInt(2, sid);
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}
	// 해당 방명록에 좋아요 누른 회원 리스트 조회
	public List<String> getLikeList(int num)
	{
		List<String> result = new ArrayList<String>();
		PreparedStatement pstmt = null;
		String sql ="";
		ResultSet rs = null;
		try
		{
			sql = "SELECT SID FROM TBL_LIKE WHERE NUM = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
				result.add(String.valueOf(rs.getInt("SID")));
		
			rs.close();
			pstmt.close();
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return result;
	}
	// 해당 방명록 좋아요 순위 조회
	public int getLikeRank(int num)
	{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		
		try
		{
			sql = "SELECT T.RANK" + 
				  " FROM(" + 
				  " SELECT NUM, DENSE_RANK() OVER(ORDER BY COUNT(NUM) DESC) AS RANK " + 
				  " FROM TBL_LIKE " + 
				  " GROUP BY NUM " + 
				  " )T" + 
				  " WHERE T.NUM=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt(1);
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return result;
	}

}
