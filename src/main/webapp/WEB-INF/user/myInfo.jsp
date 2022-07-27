<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Test Page</title>
</head>
<body>
	멤버정보 페이지 입니다
	
		
	<div>nickname : ${nickname}</div>
	<div>password : ${password}</div>
	<button onClick="location.href='/memberList'">내정보</button>
	<button onClick="location.href='/logoutProcess'">로그아웃</button>
	<button onClick="location.href='/logout'">로그아웃 안되는 버튼</button>
</body>
</html>