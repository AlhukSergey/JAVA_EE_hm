<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Продукт</title>
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
                    <li class="nav-item">
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
    <c:if test="${not empty product}">
        <h2 style="text-align: center">${product.getName()}</h2>
        <div class="container">
            <div class="row">
                <div class="col align-self-start">
                    <c:forEach items="${images}" var="image">
                        <c:if test="${image.getPrimary() == 1}">
                                <img src="${image.getImagePath()}" class="d-block" style="max-height:700px; width: auto;">
                        </c:if>
                    </c:forEach>
                    <p>${product.getDescription()}</p>
                    <p>${product.getPrice()} р.</p>
                </div>
                <div class="col align-self-end">
                    <div class="btn-lg d-grid gap-2 d-md-flex justify-content-md-end">
                        <a href="${contextPath}/shop?command=add-product-to-shopping-cart&product_id=${product.getId()}">
                            <button id="addProductToCart" class="btn btn-primary" type="button" onclick="productAddedToShoppingCartMsg()">Купить</button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function productAddedToShoppingCartMsg() {
        window.confirm("Продукт добавлен в корзину!");
    }
</script>
</body>
</html>
