<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление пользователя</title>
</head>
<body>
    <h3>Новый пользователь</h3>
    <form method="post" action="create">
        <label>Имя</label></br>
        <input name="name"/></br>

        <label>Возраст</label></br>
        <input name="age" type="number" min="0"/></br>

        <label>Почта</label></br>
        <input name="email"/></br>

        <label>Пароль</label></br>
        <input name="password"/></br></br>
        <input type="submit" value="Save"/>
    </form>
</body>
</html>
