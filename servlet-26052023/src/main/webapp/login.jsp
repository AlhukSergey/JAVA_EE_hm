<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница входа</title>
</head>
<body>
${info}
<form method="post" action="login">
    Почта: <input type="text" name="email" /></br>
    Пароль: <input type="text" name="password" /></br>
    <input type="submit" value="Войти">
</form>
</body>
</html>
