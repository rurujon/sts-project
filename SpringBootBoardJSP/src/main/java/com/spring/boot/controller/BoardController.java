package com.spring.boot.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.boot.dto.BoardDTO;
import com.spring.boot.service.BoardService;
import com.spring.boot.util.MyPage;

@Controller
public class BoardController {
	
	@Resource
	private BoardService boardService;
	
	@Autowired
	MyPage myPage;
	
	@GetMapping(value = "/")
	public ModelAndView index() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
	}
	
	@GetMapping(value = "/created.action")
	public ModelAndView created() throws Exception{
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/created");
		
		return mav;
		
	}
	
	
	@PostMapping(value = "/created.action")
	public ModelAndView created_ok(BoardDTO dto,
			HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		int maxNum = boardService.maxNum();
		
		dto.setNum(maxNum + 1);
		dto.setIpAddr(request.getRemoteAddr());
		
		boardService.insertData(dto);
		
		mav.setViewName("redirect:/list.action");
		
		return mav;
		
	}
	
	@GetMapping(value = "/list.action")
	public ModelAndView list(HttpServletRequest request) throws Exception{
		
		//넘어온 페이지 번호(Mypage,update,delete)
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null){
			
			currentPage = Integer.parseInt(pageNum);
			
		}
		
		//검색--------------------------------------
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null){
			
			//넘어온 방식이 GET 방식일때
			if(request.getMethod().equalsIgnoreCase("GET")){
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}else{
			searchKey="subject";
			searchValue="";
			
		}
		
		
		
		//검색--------------------------------------
		
		
		//전체 데이터 갯수
		int dataCount =  boardService.getDataCount(searchKey,searchValue);
		
		
		
		//한페이지에 출력할 데이터의 갯수
		int numPerPage = 5;
		
		int totalPage = myPage.getPageCount(numPerPage, dataCount);
		
		//System.out.print(totalPage); <-페이지 개수 중간에 확인
		
		//전체페이지 수보다 표시할 페이지가 큰 경우
		//표시할 페이지를 전체 페이지로 만들기 위한 코딩
		if(currentPage>totalPage){
			currentPage = totalPage;
		}
		
		//데이터베이스에서 가져올 rownum의 시작과 끝위치
		int start = (currentPage-1)*numPerPage+1;
		int end = currentPage*numPerPage;
		//=int end = start + numPerPage - 1;
		

		
		List<BoardDTO> lists = boardService.getLists(start,end,searchKey,searchValue);
		
		//검색--------------------------------
		String param = "";
		if(!searchValue.equals("")){
			
			param="?searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		//페이징 처리
		String listUrl = "/list.action" + param;
		String pageIndexList = myPage.pageIndexList(currentPage, totalPage, listUrl);
		
		//article 주소 정리
		
		String articleUrl = "/article.action";
		
		if(param.equals("")){
			articleUrl += "?pageNum=" + currentPage;
		}else{
			articleUrl += param+"&pageNum="+currentPage;
		}
		
		ModelAndView mav = new ModelAndView();
		
		//포워딩 페이지에 넘길 데이터
		mav.addObject("lists", lists);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("dataCount", dataCount);
		mav.addObject("articleUrl", articleUrl);
		
		
		mav.setViewName("bbs/list");
		
		return mav;
		
	}
	
	
	@GetMapping(value = "/article.action")
	public ModelAndView article(HttpServletRequest request) throws Exception{
		
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		//검색--------------------------------------
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
			
		if(searchValue!=null){
			
			//넘어온 방식이 GET 방식일때
			if(request.getMethod().equalsIgnoreCase("GET")){
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}else{
			searchKey="subject";
			searchValue="";
		}
			
			
			
		//검색--------------------------------------
		
		
		//조회수 증가
		boardService.updateHitCount(num);
		
		//글 가져오기
		BoardDTO dto = boardService.getReadData(num);
		
		if(dto==null){
			//String url=cp+"/list.action";
			//response.sendRedirect(url);
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/list.action");
			
			return mav;
		}
		
		//글 라인수
		int lineSu = dto.getContent().split("\n").length;
		
		//글 내용의 엔터를 <br/>로 변경
		dto.setContent(dto.getContent().replace("\n", "<br/>"));
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
				
			param+="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/article");
		
		mav.addObject("params",param);
		mav.addObject("dto",dto);
		mav.addObject("lineSu",lineSu);
		mav.addObject("pageNum",pageNum);
		
		return mav;
		
		
	}
	
	@GetMapping(value = "/updated.action")
	public ModelAndView updated(HttpServletRequest request) throws Exception{
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		//검색--------------------------------------
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
			
		if(searchValue!=null){
				
			//넘어온 방식이 GET 방식일때
			if(request.getMethod().equalsIgnoreCase("GET")){
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}else{
			searchKey="subject";
			searchValue="";
		}
			
		
		//검색--------------------------------------
		
		
		BoardDTO dto = boardService.getReadData(num);
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			
			param+="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto", dto);
		mav.addObject("pageNum", pageNum);
		mav.addObject("params", param);
		mav.addObject("searchKey", searchKey);
		mav.addObject("searchValue", searchValue);

		mav.setViewName("bbs/updated");
		
		return mav;
	}
		
	
	
	
	@PostMapping(value = "/updated.action")
	public ModelAndView updated_ok(BoardDTO dto, HttpServletRequest request) throws Exception{
		
		String pageNum = request.getParameter("pageNum");
		//검색--------------------------------------
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null){
			
			//넘어온 방식이 GET 방식일때
			if(request.getMethod().equalsIgnoreCase("GET")){
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}else{
			searchKey="subject";
			searchValue="";
		}
			
			
			
		//검색--------------------------------------
		
		

		
		boardService.updateData(dto);
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			
			param="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/list.action?" + param);
		
		return mav;

	}
	
	
	
	@GetMapping(value = "/deleted_ok.action")
	public ModelAndView deleted_ok(HttpServletRequest request) throws Exception{
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		//검색--------------------------------------
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue!=null){
			
			//넘어온 방식이 GET 방식일때
			if(request.getMethod().equalsIgnoreCase("GET")){
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}else{
			searchKey="subject";
			searchValue="";
		}		
		//검색--------------------------------------
		
		
		
		boardService.deleteData(num);
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			
			param="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------

		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/list.action?" + param);
		
		return mav;
		
	}
	

}
