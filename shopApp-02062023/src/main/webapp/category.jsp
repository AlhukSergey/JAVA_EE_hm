<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        a {
            font-size: 14px;
            font-weight: 700
        }

        .superNav {
            font-size: 13px;
        }

        .form-control {
            outline: none !important;
            box-shadow: none !important;
        }

        @media screen and (max-width: 540px) {
            .centerOnMobile {
                text-align: center
            }
        }
    </style>
    <title>${category.getName}</title>
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
                        <a class="nav-link mx-2 text-uppercase active" aria-current="page" href="#">Главная</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="#">Каталог</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="#">О нас</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto ">
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="#"><img
                                style="border-radius: 50%; max-height: 25px;"
                                src="data:image/png;base64,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"
                                class="fa-solid fa-cart-shopping me-1"> Корзина</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link mx-2 text-uppercase" href="#"><img
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
    <h1 style="text-align: center">${categoryName}</h1>
    <p>Товары в наличии</p>
    <div class="container-fluid">
        <c:if test="${not empty products}">
            <div class="row">
                <c:forEach items="${products}" var="product">
                    <div class="card w-25 m-1" type="product">
                        <div class="card-body">
                            <p>${product.getName()}</p>
                            <img class="card-img" style="max-height:300px"
                                 src="${product.getImagePath()}" alt="Card image">
                            <p>${product.getDescription()}</p>
                            <p>${product.getPrice()} р.</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
