package com.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdbc.dto.BoardDTO;



public class BoardDAO {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
	}
	
	Connection conn = null;
	
	public int getMaxNum() {
		
		int maxNum = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "select nvl(max(num),0) from board";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				maxNum = rs.getInt(1);
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return maxNum;
	}
	
	public int insertData(BoardDTO dto) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "insert into board (num,name,pwd,email,subject,content,ipAddr,created,hitCount) ";
			sql+= "values (?,?,?,?,?,?,?,SYSDATE,0)";
			
			pstmt = conn.prepareStatement(sql);
			
		
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3,  dto.getPwd());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getSubject());
			pstmt.setString(6, dto.getContent());
			pstmt.setNString(7, dto.getIpAddr());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			

			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
		
		
	}
	
	public int getDataCount(String searchKey, String searchValue) {
		
		int totalDataCount = 0;
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		String sql;
	
		try {
			
			searchValue = "%" + searchValue + "%";
			
			sql = "select nvl(count(*),0) from board ";
			sql+="where " + searchKey + " like ?";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchValue);
		
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				totalDataCount = rs.getInt(1);
			}
		
			rs.close();
			pstmt.close();
		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return totalDataCount;
		
	}
	
	public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue){
		
		List<BoardDTO> lists = new ArrayList<BoardDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			searchValue="%" + searchValue + "%";
			
			sql = "select * from (";
			sql+= "select rownum rnum, data.* from (";
			sql+= "select num,name,subject,hitcount,";
			sql+= "to_char(created,'YYYY-MM-DD') created ";
			sql+= "from board where " + searchKey + " like ? ";
			sql+= "order by num desc) data) "; 
			sql+= "where rnum>=? and rnum<=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, searchValue);//검색
			pstmt.setInt(2, start);			//검색
			pstmt.setInt(3, end);			//검색
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				lists.add(dto);
				
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return lists;
		
	}
	
	//조회수 증가시키는
	public int updateHitCount(int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			
			sql = "update board set hitCount = hitCount + 1 ";
			sql+= "where num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public BoardDTO getReadData(int num){
		
		BoardDTO dto= null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "select num,name,pwd,email,subject,content, ";
			sql+= "ipAddr,hitCount,created from board where num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto = new BoardDTO();
				
				dto.setNum(rs.getInt("num"));
				dto.setName(rs.getString("name"));
				dto.setPwd(rs.getString("pwd"));
				dto.setEmail(rs.getString("email"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setIpAddr(rs.getString("ipAddr"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				
			}
			
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
		
	}
	
	
	public int updateData(BoardDTO dto) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "update board set name=?,pwd=?,email=?,subject=?,";
			sql+= "content=? where num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getSubject());
			pstmt.setString(5, dto.getContent());
			pstmt.setInt(6, dto.getNum());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public int deleteData(int num) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql="delete board where num=?";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		
		}
		
		return result;
	}
	
	
}
