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
        <span>
            ${info}
        </span>
        <div class="tabs">
            <div class="col-md-auto tabs__links">
                <a href="#content-1">Личные данные</a>
                <a href="#content-2">Сменить пароль</a>
                <a href="#content-3">Текущие заказы</a>
            </div>

            <div class="userData" id="content-1">
                <form action="/shop" autocomplete="off" method="post" class="needs-validation">
                    <input type="hidden" name="command" value="update-user-data"/>
                    <div class="form-group">
                        <label for="name">Имя:</label>
                        <input type="text" class="form-control w-25" id="name" value="${name}" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="surname">Фамилия:</label>
                        <input type="text" class="form-control w-25" id="surname" value="${surname}" name="surname" required>
                    </div>
                    <div class="form-group">
                        <label for="birthday">Дата рождения:</label>
                        <input type="date" class="form-control w-25" id="birthday" value="${birthday}" name="birthday">
                    </div>
                    <div class="form-group">
                        <label for="email">Почта:</label>
                        <input type="text" class="form-control w-25" id="email" value="${email}" name="email" required>
                    </div>
                    <button id="formDataBtn" type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>

            <div class="changePass" id="content-2">
                <form method="post" autocomplete="off" action="/shop" class="needs-validation">
                    <input type="hidden" name="command" value="update-user-data"/>
                    <div class="form-group">
                        <label for="old_password">Старый пароль:</label>
                        <input type="text" class="form-control w-25" id="old_password"
                               placeholder="Введите старый пароль" name="old_password" required>
                    </div>
                    <div class="form-group">
                        <label for="new_password">Новый пароль:</label>
                        <input type="text" class="form-control w-25" id="new_password"
                               placeholder="Введите новый пароль" name="new_password" required>
                    </div>
                    <div class="form-group">
                        <label for="new_password_rep">Введите новый пароль повторно:</label>
                        <input type="text" class="form-control w-25" id="new_password_rep"
                               placeholder="Введите новый пароль повторно" name="new_password_rep" required>
                    </div>
                    <button id="formPassBtn" type="submit" class="btn btn-primary">Сохранить</button>
                </form>
            </div>

            <div class="activeOrders" id="content-3">
                <c:if test="${not empty orders}">
                    <div class="row">
                        <c:forEach items="${orders}" var="order">
                            <div class="card w-25 m-1" type="order">
                                <div class="card-body">
                                    <c:if test="${order.getOrderStatus().toString().equals('ACTIVE')}">
                                        <h5 class="card-title">Заказ №${order.getId()} / ${order.getCreatedAt()}</h5>
                                        <div>${order.getOrderStatus().toString()}</div>
                                        <div>${order.getPrice()} р.</div>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>

            <div class="finishedOrders" id="content-4">
                <c:if test="${not empty orders}">
                    <div class="row">
                        <c:forEach items="${orders}" var="order">
                            <div class="card w-25 m-1" type="order">
                                <div class="card-body">
                                    <c:if test="${order.getOrderStatus().toString().equals('FINISHED')}">
                                        <h5 class="card-title">Заказ №${order.getId()} / ${order.getCreatedAt()}</h5>
                                        <div>${order.getOrderStatus().toString()}</div>
                                        <div>${order.getPrice()} р.</div>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script src="script/validation-script.js"></script>
</body>
</html>
