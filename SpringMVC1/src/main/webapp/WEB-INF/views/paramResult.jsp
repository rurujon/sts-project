<%@page import="com.exe.springmvc.PersonDTO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%	
	String cp = request.getContextPath();
	request.setCharacterEncoding("UTF-8");	

	String name = request.getParameter("name");
	String phone = request.getParameter("phone");
	String email = request.getParameter("email");
	
	PersonDTO dto = (PersonDTO)request.getAttribute("dto");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<h2>param Result</h2>
이름 : <%=name %>
전화 : <%=phone %>
이메일 : <%=email %>


<br/><br/>

<h2>modelAndview</h2>
<%if(dto!=null){%>
이름 : <%=dto.getName() %><br/>
전화 : <%=dto.getPhone() %><br/>
이메일 : <%=dto.getEmail() %><br/>
<%}else{ %>
	데이터 없음<br/>
<%} %>




</body>
</html>