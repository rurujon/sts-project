package com.spring.boot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.boot.dto.BoardDTO;

@Mapper
public interface BoardMapper {
	
	
	//보드매퍼의 맥스넘과 인서트데이터를 호출할 메서드를 만들 것이다.
	public int maxNum() throws Exception;
	
	public void insertData(BoardDTO dto) throws Exception;
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<BoardDTO> getLists(int start, int end, String searchKey, String searchValue);
	
	public BoardDTO getReadData(int num) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public void updateData(BoardDTO dto) throws Exception;
	
	public void deleteData(int num) throws Exception;

}
