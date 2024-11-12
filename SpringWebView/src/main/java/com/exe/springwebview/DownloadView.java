package com.exe.springwebview;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		//response.setContentType("application/octet-stream");
		response.setContentType("application/unknown");
		
		response.addHeader("Content-Disposition",
				"Attatchment;filename=\"8^mo3R.jpg\"");
		//자기가 올린 파일 이름
		
		String path = request.getSession()
				.getServletContext().getRealPath("/WEB-INF/files/8^mo3R.jpg");
		
		FileInputStream fis = new FileInputStream(path);
		OutputStream os = response.getOutputStream();
		
		FileCopyUtils.copy(fis, os);
		
		fis.close();
		os.close();
		
		
	}
	
	

}
