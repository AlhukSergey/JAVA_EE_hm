<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <title>Личный кабинет</title>
    <style>
        *,
        *::before,
        *::after {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
            font-size: 16px;
            font-weight: 400;
            line-height: 1.5;
            color: #212529;
            text-align: left;
            background-color: #fff;
        }

        .container {
            margin-left: auto;
            margin-right: auto;
            padding-left: 15px;
            padding-right: 15px;
        }

        .tabs {
            display: flex;
            flex-direction: column;
        }

        .tabs__links {
            display: flex;
            flex-direction: row;
            order: 0;
            white-space: nowrap;
            margin-bottom: 15px;
            background-color: #fff;
            border: 1px solid #e3f2fd;
            box-shadow: 0 2px 4px 0 #e3f2fd;
        }

        .tabs__links > a {
            display: inline-block;
            text-decoration: none;
            color: #1976d2;
            padding: 6px 10px;
            text-align: center;
        }

        .tabs__links > a:hover {
            background-color: rgba(227, 242, 253, 0.3);
        }

        .tabs > #content-1:target ~ .tabs__links > a[href="#content-1"],
        .tabs > #content-2:target ~ .tabs__links > a[href="#content-2"],
        .tabs > #content-3:target ~ .tabs__links > a[href="#content-3"],
        .tabs > #content-4:target ~ .tabs__links > a[href="#content-4"]{
            background-color: #bbdefb;
            cursor: default;
        }

        .tabs > div:not(.tabs__links) {
            display: none;
            order: 1;
            flex-grow: 1;
        }

        @media (min-width: 576px) {
            .tabs {
                flex-direction: row;
            }

            .tabs__links {
                flex-direction: column;
                border: none;
                box-shadow: none;
            }

            .tabs__links > a {
                border: 1px solid #e3f2fd;
                box-shadow: 0 2px 4px 0 #e3f2fd;
                margin-bottom: 8px;
            }

            .tabs__links > a:last-child {
                margin-bottom: 0;
            }

            .tabs > div:not(.tabs__links) {
                padding-left: 15px;
            }
        }

        .tabs > div:target {
            display: block;
        }
    </style>
</head>
<body>
<div class="superNav border-bottom py-2 bg-light" style="border-radius: 25px;">
    <nav class="navbar navbar-expand-lg bg-white sticky-top navbar-light p-3 shadow-sm">
        <div class="container">
            <a class="navbar-brand" href="#"><i class="fa-solid fa-shop me-2"></i> <strong>SportMaster</strong></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                    aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class=" collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav ms-auto ">
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase active" aria-current="page"
                           href="/shop?command=redirect-to-home-page">Главная</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="/shop?command=redirect-to-home-page">Каталог</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="/shop?command=redirect-to-my-page">О нас</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto ">
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="/shop?command=redirect-to-shopping-cart-page"><img
                                style="border-radius: 50%; max-height: 25px;"
                                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRL4ELxVekG-GUDCb6BMDCg-zHhKWfLdNARGCvloOGOSWfthtZAwylrlCT20AT0zGm02LQ&usqp=CAU"
                                class="fa-solid fa-cart-shopping me-1"> Корзина</a>
                    </li>
                    <li class="nav-item" hidden="hidden">
                        <a class="nav-link mx-2 text-uppercase" href="/shop?command=redirect-to-user-account-page"><img
                                style="border-radius: 50%; max-height: 25px;"
                                src="https://w7.pngwing.com/pngs/178/595/png-transparent-user-profile-computer-icons-login-user-avatars-thumbnail.png"
                                class="fa-solid fa-circle-user me-1"> Аккаунт</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <h2 style="text-align: center">Личный кабинет</h2>
    <div class="row">
        <div class="col tabs">
            <div class="col-md-auto d-flex flex-column mb-3 tabs__links">
                <a href="#content-1">Личные данные</a>
                <a href="#content-2">Сменить пароль</a>
                <a href="#content-3">Текущие заказы</a>
                <a href="#content-4">История заказов</a>
            </div>
            
            ${info}
            <div class="userDate" id="content-1">
                <div>
                    <span>Имя: </span>
                    <span>${name}</span>
                </div>
                <div>
                    <span>Фамилия: </span>
                    <span>${surname}</span>
                </div>
                <div>
                    <span>Дата рождения: </span>
                    <span>${birthday}</span>
                </div>
                <div>
                    <span>Почта: </span>
                    <span>${email}</span>
                </div>
            </div>
            <div class="changePass" id="content-2">
                <form method="post" action="/shop">
                    <input type="hidden" name="command" value="change-password"/>
                    <div class="form-group">
                        <label for="oldPassword">Старый пароль:</label>
                        <input type="text" class="form-control w-25" id="oldPassword"
                               placeholder="Введите старый пароль" name="oldPassword"
                               required>
                        <div class="invalid-feedback">Поле должно быть заполнено!</div>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">Новый пароль:</label>
                        <input type="text" class="form-control w-25" id="newPassword" placeholder="Введите новый пароль"
                               name="newPassword"
                               required>
                        <div class="invalid-feedback">Поле должно быть заполнено!</div>
                    </div>
                    <div class="form-group">
                        <label for="newPasswordRep">Введите новый пароль повторно:</label>
                        <input type="text" class="form-control w-25" id="newPasswordRep"
                               placeholder="Введите новый пароль повторно" name="newPasswordRep"
                               required>
                        <div class="invalid-feedback">Поле должно быть заполнено!</div>
                    </div>
                    <button id="formBtn" type="submit" class="btn btn-primary">
                        Отправить
                    </button>
                </form>
            </div>
            <div class="currOrder" id="content-3">
                Содержимое 3...
            </div>
            <div class="orderHistory" id="content-4">
                Содержимое 4...
            </div>
        </div>
    </div>
</div>
</body>
</html>
