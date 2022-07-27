<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
</head>
<body>
    <div style="color:red">유저 로그인 페이지</div>
    <form action="/login/loginProcess" method="post">
        <div>아이디 : <input type="text" name="username"/></div>
        <div>비번 : <input type="password" name="password"/></div>
        <input type="submit"/>
    </form>
</body>
</html>