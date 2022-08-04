<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
    <div style="color:red">유저 로그인 페이지</div>
    <div>아이디 : <input type="text" name="username" id="principal"/></div>
    <div>비번 : <input type="password" name="password" id="credential"/></div>
    <button onClick="javascript:login();">로그인</button>
</body>
</html>

<script>
    function login() {
        var id = document.getElementById("principal").value;
        var pw = document.getElementById("credential").value;

        let post_data = {
            username: id,
            password: pw
        }

        $.ajax({
            url: '/login/loginProcess',
            method: 'POST',
            contentType: "application/json; charset=utf-8",
            //data : {'username':id, 'password':pw},
            data: JSON.stringify(post_data),
            success: function (result) {
                // result{"success":true,"code":0,"msg":"로그인 성공","data":null}
                alert("result" + JSON.stringify(result));
                window.location.href = '/main';
            },
            error: function (error) {
                // {"readyState":4,"responseText":"{\"success\":false,\"code\":-100,\"msg\":\"아이디 입력해주세요\",\"data\":null}","responseJSON":{"success":false,"code":-100,"msg":"아이디 입력해주세요","data":null},"status":400,"statusText":"error"}
                alert(JSON.stringify(error) + " error : " + error.responseJSON.msg);
            }
        });
    }
</script>
<%--$.ajax--%>
<%--    method : post--%>
<%--    data : {--%>
<%--    },--%>
<%--    contentType : aplication/json utf="8" , www.form---%>

<%--success (result)--%>
<%--    window.location = "";--%>
<%--error (error)--%>
<%--    console.log(error);--%>


