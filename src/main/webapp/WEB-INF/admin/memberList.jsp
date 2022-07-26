<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
</head>
<body>
    <div style="color:#f80808">관리자 페이지</div>
    회원목록 입니다
    <c:forEach var="result" items="${memberList}">
        <div>이름 : ${result.name}</div>
    </c:forEach> 
</body>
</html>