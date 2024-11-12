<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>

<br/><br/><br/>

<div style="padding-left: 20px;">

<h2>SpringMVC Custom view</h2>

<h3>1. <a href="simpleCustomView.action">Simple custom View</a> </h3>
<h3>2. <a href="pdfView.action">PDF View</a> </h3>
<h3>3. <a href="excelView.action">Excel View</a> </h3>
<h3>4. File upload</h3>
<form action="upload.action" method="post"
enctype="multipart/form-data">
<input type="file" name="upload"/><br/>
<input type="submit" value="전송"/><br/>
</form>
<br/>

<h3>5. <a href="download.action">File Download</a> </h3>


</div>

</body>
</html>
