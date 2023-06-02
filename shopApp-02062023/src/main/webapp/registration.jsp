<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Страница регистрации на сайте</title>
</head>
<body>
<div class="container">
    <div class="col-md-8 offset-md-4">
        <h2>Регистрация</h2>
        <p>Пожалуйста, введите свои данные:</p>
        <form method="post" action="/registration" class="needs-validation" novalidate>
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
<script>
    // Disable form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Get the forms we want to add validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();

    // Disable submit button if all fields are empty
    document.getElementById('loginBtn').disabled = true;

    document.getElementById('name').addEventListener('keyup', e => {
        //Check for the input's value
        if (e.target.value === "") {
            document.getElementById('loginBtn').disabled = true;
        } else {
            document.getElementById('loginBtn').disabled = false;
        }
    });

    document.getElementById('surname').addEventListener('keyup', e => {
        //Check for the input's value
        if (e.target.value === "") {
            document.getElementById('loginBtn').disabled = true;
        } else {
            document.getElementById('loginBtn').disabled = false;
        }
    });

    document.getElementById('email').addEventListener('keyup', e => {
        //Check for the input's value
        if (e.target.value === "") {
            document.getElementById('loginBtn').disabled = true;
        } else {
            document.getElementById('loginBtn').disabled = false;
        }
    });

    document.getElementById('password').addEventListener('keyup', e => {
        //Check for the input's value
        if (e.target.value === "") {
            document.getElementById('loginBtn').disabled = true;
        } else {
            document.getElementById('loginBtn').disabled = false;
        }
    });
</script>
</body>
</html>
