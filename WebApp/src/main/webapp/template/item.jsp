<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<body>
<h2>Hello World!</h2>
<hr/>
<c:out value="${time}"/>
<br/>
${requestScope.time}<br/>
</body>
</html>
