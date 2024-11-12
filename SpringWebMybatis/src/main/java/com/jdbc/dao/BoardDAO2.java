package com.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;

import com.jdbc.dto.BoardDTO;



public class BoardDAO2 {
	
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	
	
	public int getMaxNum() {
		
		int maxNum = 0;
		
		maxNum = sessionTemplate.selectOne("com.board.maxNum");
		
		

		
		return maxNum;
	}
	
	public void insertData(BoardDTO dto) {
		
		sessionTemplate.insert("com.board.insertData", dto);
		
		
	}
	
	public int getDataCount(String searchKey, String searchValue) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		int totalDataCount =
				sessionTemplate.selectOne("com.board.getDataCount",params);
		

		return totalDataCount;
		
	}
	
	public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("start", start);
		params.put("end", end);
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		List<BoardDTO> lists =
				sessionTemplate.selectList("com.board.getLists", params);
		

		return lists;
		
	}
	
	//조회수 증가시키는
	public void updateHitCount(int num) {
		
		sessionTemplate.update("com.board.updateHitCount", num);
		
	}
	
	public BoardDTO getReadData(int num){
		
		BoardDTO dto =
				sessionTemplate.selectOne("com.board.getReadData", num);
		
		return dto;
		
	}
	
	
	public void updateData(BoardDTO dto) {
		
		sessionTemplate.update("com.board.updateData", dto);
		
	}
	
	public void deleteData(int num) {
		
		sessionTemplate.delete("com.board.deleteData", num);
	}
	
	
}
