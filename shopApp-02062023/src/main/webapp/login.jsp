<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Страница входа</title>
</head>
<body>
<div class="container">
    <div class="col-md-8 offset-md-4">
        <h2>Вход</h2>
        ${info}
        <p>Пожалуйста, введите свои данные:</p>
        <form method="post" action="/shop" class="needs-validation">
            <input type="hidden" name="command" value="login"/>
            <div class="form-group">
                <label for="email">Логин:</label>
                <input type="text" class="form-control w-25" id="email" placeholder="Введите email" name="email"
                       required>
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль" name="password"
                       required>
            </div>
            <button id="loginBtn" type="submit" class="btn btn-primary">Войти</button>
        </form>
        <a class="btn btn-primary" href="/shop?command=redirect-to-registration-page">На страницу регистрации</a>
    </div>
</div>
<script src="script/script.js">
</script>
</body>
</html>
