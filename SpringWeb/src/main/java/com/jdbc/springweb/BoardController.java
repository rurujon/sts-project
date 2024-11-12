package com.jdbc.springweb;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jdbc.dao.BoardDAO;
import com.jdbc.dao.BoardDAO2;
import com.jdbc.dto.BoardDTO;
import com.jdbc.util.MyPage;

@Controller
public class BoardController {
	
	@Autowired
	@Qualifier("boardDAO")
	BoardDAO dao;

	@Autowired
	MyPage myPage;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "index";
	}
	/*
	@RequestMapping(value = "/created.action",
			method = {RequestMethod.GET})
	public String created() throws Exception{
		return "bbs/created";
	}
	*/
	//두 개는 똑같은 거다.
	@RequestMapping(value = "/created.action")
	public ModelAndView created() {
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/created");
		
		return mav;
	}
	
	
	@RequestMapping(value = "/created_ok.action",
			method = {RequestMethod.POST})
	public String created_ok(BoardDTO dto, HttpServletRequest request) throws Exception{
		
		int maxNum = dao.getMaxNum();
		
		dto.setNum(maxNum+1);
		dto.setIpAddr(request.getRemoteAddr());
		
		dao.insertData(dto);
		
		return "redirect:/list.action";
		

	}
	
	
	@RequestMapping(value = "/list.action",
			method = {RequestMethod.GET, RequestMethod.POST})
	public String list(HttpServletRequest request) throws Exception{
		
		String cp = request.
				getContextPath();
		
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
		int dataCount =  dao.getDataCount(searchKey,searchValue);
		
		
		
		//한페이지에 출력할 데이터의 갯수
		int numPerPage = 3;
		
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
		

		
		List<BoardDTO> lists = dao.getLists(start,end,searchKey,searchValue);
		
		//검색--------------------------------
		String param = "";
		if(!searchValue.equals("")){
			
			param="?searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		//페이징 처리
		String listUrl = cp + "/list.action"+param;
		String pageIndexList = myPage.pageIndexList(currentPage, totalPage, listUrl);
		
		//article 주소 정리
		
		String articleUrl = cp + "/article.action";
		
		if(param.equals("")){
			articleUrl += "?pageNum=" + currentPage;
		}else{
			articleUrl += param+"&pageNum="+currentPage;
		}
		
		//포워딩 페이지에 넘길 데이터
		request.setAttribute("lists", lists);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		request.setAttribute("articleUrl", articleUrl);

		return "bbs/list";
		
	}
	
	//@RequestMapping(value = "/article.action",
		//	method = {RequestMethod.GET, RequestMethod.POST})
	//public String article(HttpServletRequest request, HttpServletResponse response) throws Exception{

	@RequestMapping(value = "/article.action",
			method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView article(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	
		String cp = request.getContextPath(); 
		
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
		dao.updateHitCount(num);
		
		//글 가져오기
		BoardDTO dto = dao.getReadData(num);
		
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
		/*
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
		return "bbs/article";
		*/
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("bbs/article");
		
		mav.addObject("params",param);
		mav.addObject("dto",dto);
		mav.addObject("lineSu",lineSu);
		mav.addObject("pageNum",pageNum);
		
		return mav;
		
	}
	
	
	@RequestMapping(value = "/updated.action",
		method = {RequestMethod.GET, RequestMethod.POST})
	public String updated(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String cp = request.getContextPath();
		
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
		
		
		BoardDTO dto = dao.getReadData(num);
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			
			param+="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("params", param);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchValue", searchValue);

		return "bbs/updated";
		
	}
	
	@RequestMapping(value = "/updated_ok.action",
			method = {RequestMethod.GET, RequestMethod.POST})
	public String updated_ok(BoardDTO dto, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
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
		
		

		
		dao.updateData(dto);
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			
			param="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------
		
		return "redirect:/list.action?" + param;


	}
	
	@RequestMapping(value = "/deleted_ok.action",
			method = {RequestMethod.GET, RequestMethod.POST})
	public String deleted_ok(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
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
		
		
		
		dao.deleteData(num);
		
		//검색--------------------------------
		String param = "pageNum=" + pageNum;
		if(!searchValue.equals("")){
			
			param="&searchKey=" + searchKey;
			param+="&searchValue="+URLEncoder.encode(searchValue, "UTF-8");	
		}
		//검색-----------------------------------

		return "redirect:/list.action";
		
		
	}


	
}
