<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<h3><a href = "hello.action">Spring 환영 메세지</a></h3>

<h2>Spring MVC Controller Test</h2>

<h3>1. <a href="test/param.action?name=수지&
phone=010-1111-1111&email=suzi@naver.com">GET 방식 테스트</a></h3>

<h3>2. post 방식 테스트</h3>
<form action="test/param.action" method="post">

이름 : <input type="text" name="name"/><br/>
전화 : <input type="text" name="phone"/><br/>
메일 : <input type="text" name="email"/><br/>
<input type="submit" value="전송"/>

</form>

<br/>

<h3>3. <a href="test/mav.action?name=somin&
phone=010-2222-2222&email=somin@naver.com">Model And View GET 테스트</a></h3>

<h3>4. Model and View post 방식 테스트</h3>
<form action="test/mav.action" method="post">

이름 : <input type="text" name="name"/><br/>
전화 : <input type="text" name="phone"/><br/>
메일 : <input type="text" name="email"/><br/>
<input type="submit" value="전송"/>

</form>

<br/>

<h3>5. <a href="test/redirect.action">리다이렉트 테스트</a></h3>

</body>
</html>
