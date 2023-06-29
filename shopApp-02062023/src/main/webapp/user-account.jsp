<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/user-account.css">
    <title>Личный кабинет</title>
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
                        <input type="text" class="form-control w-25" id="newPassword"
                               placeholder="Введите новый пароль"
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
            <div class="activeOrders" id="content-3">
                <c:if test="${not empty activeOrders}">
                    <div class="row">
                        <c:forEach items="${activeOrders}" var="order">
                            <div class="card w-25 m-1" type="order">
                                <div class="card-body">
                                    <h5 class="card-title">Заказ №${order.getId()} / ${order.getCreatedAt()}</h5>
                                    <div>${order.getOrderStatus().toString()}</div>
                                    <div>${order.getPrice()} р.</div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
