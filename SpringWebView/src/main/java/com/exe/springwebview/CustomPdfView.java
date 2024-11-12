package com.exe.springwebview;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class CustomPdfView extends AbstractPdfView{

	//AbstractPdfView는 클래스를 pdf 형식의 view로 만든다.
	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		Chapter chapter = new Chapter(new Paragraph("Spring Message"),1);
		chapter.add(new Paragraph((String)model.get("message")));
		
		document.add(chapter);
		
		
	}

}
