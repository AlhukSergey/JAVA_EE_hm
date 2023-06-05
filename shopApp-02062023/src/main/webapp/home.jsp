<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>Категории</title>
</head>
<body>
<div class="container">
    ${info}
    <h2 style="text-align: center">Популярные категории</h2>
    <div class="container-fluid">
        <c:if test="${not empty categories}">
            <div class="row justify-content-center">
                <c:forEach items="${categories}" var="category">
                    <div class="card w-25 m-1" type="category">
                        <a id="${category.getId()}" href='<c:url value="/category?id=${category.getId()}&name=${category.getName()}"  />'>
                            <div class="card-body">
                                <h5 class="card-title">${category.getName()}</h5>
                                <img class="card-img" style="max-height:300px" src="${category.getImagePath()}"
                                     alt="Card image">
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
