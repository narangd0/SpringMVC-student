<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>학생 정보 조회</title>
</head>
<body>
<a href="/">첫 화면으로 이동</a></br>
이름: ${student.name}<br/>
이메일: ${student.email}<br/>
<br/>
<a href="/student/${student.id}/modify">정보 수정</a><br/>
</body>
</html>
