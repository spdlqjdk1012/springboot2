<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
</head>
<body>
    <div style="color:red">유저 페이지</div>
    메인화면 입니다
    <button onClick="location.href='/logoutProcess'">로그아웃</button>
    <button onClick="location.href='/admin/memberList'">관리자페이지</button>
</body>
</html>