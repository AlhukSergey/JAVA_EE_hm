<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Страница регистрации</title>
</head>
<body>
<div class="container">
    <div class="col-md-8 offset-md-4">
        <h2>Регистрация</h2>
        ${info}
        <p>Пожалуйста, введите свои данные:</p>
        <form method="post" class="needs-validation">
            <input type="hidden" name="command" value="registration-user"/>
            <div class="form-group">
                <label for="name">Имя:</label>
                <input type="text" class="form-control w-25" id="name" placeholder="Введите имя" name="name"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <div class="form-group">
                <label for="surname">Фамилия:</label>
                <input type="text" class="form-control w-25" id="surname" placeholder="Введите фамилию" name="surname"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <div class="form-group">
                <label for="birthday">Дата рождения:</label>
                <input type="date" class="form-control w-25" id="birthday" name="birthday"
                       required>
                <div class="invalid-feedback">Введите дату рождения!</div>
            </div>
            <div class="form-group">
                <label for="email">Почта:</label>
                <input type="text" class="form-control w-25" id="email" placeholder="Введите email" name="email"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль" name="password"
                       required>
                <div class="invalid-feedback">Поле должно быть заполнено!</div>
            </div>
            <button id="loginBtn" type="submit" class="btn btn-primary">Зарегистрироваться</button>
        </form>
    </div>
</div>
<script src="script/script.js"></script>
</body>
</html>
