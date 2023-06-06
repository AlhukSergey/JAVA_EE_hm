<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">
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
                        <a href='<c:url value="/category?id=${category.getId()}&name=${category.getName()}"  />'>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>
