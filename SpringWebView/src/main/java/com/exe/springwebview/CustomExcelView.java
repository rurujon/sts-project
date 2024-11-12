package com.exe.springwebview;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CustomExcelView extends AbstractJExcelView{
	//excel 연동
	
	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			WritableWorkbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//다운로드 받는 핵심 코딩
		response.addHeader("content-Disposition",
				"Attachment;FileName=sales.xml");
		
		WritableSheet sheet =
				workbook.createSheet("판매보고서", 0);
		
		sheet.addCell(new Label(0,0,"년도"));
		sheet.addCell(new Label(1,0,"판매량"));
		
		//임의의 값
		for(int i=2001;i<2015;i++) {
			sheet.addCell(new Label(0,i-2000,String.format("%d", i)));
			sheet.addCell(new Label(1,i-2000,String.format("%d", i+100000)));
		}
		
	}
	

}
