package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.BoardDTO;

public interface BoardService {
	
	//보드 매퍼.java와 메서드 이름이 완전히 똑같아야한다.
	//보드매퍼 메서드와 보드서비스 메서드 두 개를 똑같이 하면 데이터가 흘러 DB를 엑세스할 수 있다.
	
	public int maxNum() throws Exception;
	
	public void insertData(BoardDTO dto) throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue);
	
	public BoardDTO getReadData(int num) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public void updateData(BoardDTO dto) throws Exception;
	
	public void deleteData(int num) throws Exception;


}
