<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/search.css">
    <title>Поисковая страница</title>
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
    <h1 style="text-align: center">Поисковая страница</h1>

    <div class="row d-flex justify-content-center align-items-center">
        <div class="col-md-8">
            <div class="search">
                <i class="fa fa-search"></i>
                <form method="post" action="/shop">
                    <input type="hidden" name="command" value="search"/>
                    <input type="text" class="form-control" placeholder="Введите название либо описание товара..." name="search_param">
                    <button class="btn btn-primary">Найти</button>
                </form>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <c:if test="${not empty products}">
            <div class="row">
                <c:forEach items="${products}" var="product">
                    <div class="card w-25 m-1" type="product">
                        <div class="card-body" style="flex: 1 1 auto;">
                            <a href='<c:url value="/shop?command=redirect-to-product-page&product_id=${product.getId()}"/>'>
                                <h5 class="card-title">${product.getName()}</h5>
                                <c:forEach items="${images}" var="image">
                                    <c:if test="${product.getId() == image.getProductId() && image.getPrimary() == 1}">
                                        <img class="card-img card-scale" style="max-height:300px"
                                             src="${image.getImagePath()}" alt="Card image">
                                    </c:if>
                                </c:forEach>
                            </a>
                            <p class="card-text">${product.getDescription()}</p>
                        </div>
                        <div class="card-footer" style="flex: 0 1 auto;">${product.getPrice()} р.</div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <span>
            ${info}
        </span>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous">
</script>
</body>
</html>
