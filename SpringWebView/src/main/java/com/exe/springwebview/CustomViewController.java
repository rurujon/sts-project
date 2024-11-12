package com.exe.springwebview;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CustomViewController {
	
	@RequestMapping(value = "/simpleCustomView.action")
	public ModelAndView customView() {
		
		ModelAndView mav = new ModelAndView();
		
		//mav.setViewName("simpleCustomView");	//jsp
		
		//customView란 클래스를 view로 만든 것

		mav.setView(new SimpleCustomView());//클래스를 view처럼 사용.
		mav.addObject("message","SimpleCustomView Class 입니다.");	
		
		return mav;
				
	}
	
	@RequestMapping(value = "/pdfView.action")
	public ModelAndView getPdfView() {
		
		ModelAndView mav = new ModelAndView();
		

		mav.setView(new CustomPdfView());//클래스를 view처럼 사용.
		mav.addObject("message","PDF File");	
		
		return mav;
				
	}
	
	@RequestMapping(value = "/excelView.action")
	public ModelAndView getExcelView() {
		
		ModelAndView mav = new ModelAndView();
		

		mav.setView(new CustomExcelView());//클래스를 view처럼 사용.
			
		
		return mav;
				
	}
	
	@RequestMapping(value = "/upload.action",
			method = RequestMethod.POST)
	public String upload(MultipartHttpServletRequest request) {
		
		//web-inf 폴더 안의 영역은 보안영역으로써, 외부에서 접근이 아예 불가능하다.
		//따라서 외부에 보여지게 될 파일들은 전부 web-inf가 아닌 외부의 resource 영역에 넣어야한다.
		
		//WEB-INF는 외부에서 접근 금지 영역이기때문에
		//이미지를 보기위해서는 webapp 폴더밑에 폴더(image)를 만들거나 
		//또는 resources에 만들어줌
		//그리고 위의 저장 경로를 다음과 같이 바꿔줌
		//.getServletContext().getRealPath("/resources/image");
		              //.getServletContext().getRealPath("/image")
		//webapp/image에 폴더를 만든경우 servlet-context.xml 에서
		//<resources mapping="/resources/**" location="/resources/" />를
		//<resources mapping="/**" location="/" />로 수정
		
		
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/files");
		
		MultipartFile file = request.getFile("upload");
		
		if(file!=null && file.getSize()>0) {
			
			try {
				
				InputStream is = file.getInputStream();
				
				FileOutputStream fos =
						new FileOutputStream(path + "/" + 
								file.getOriginalFilename());
				
				FileCopyUtils.copy(is, fos);
				
				is.close();
				fos.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		return "uploadResult";
	
		
	}
	

	
	
	@RequestMapping(value = "/download.action")
	public ModelAndView download() {
		
		ModelAndView mav = new ModelAndView();
		

		mav.setView(new DownloadView());//클래스를 view처럼 사용.
			
		
		return mav;
				
	}
	
	
	

}
