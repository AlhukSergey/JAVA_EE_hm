<%--
  Created by IntelliJ IDEA.
  User: Alshuk
  Date: 20.05.2023
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Results</title>
</head>
<body>
<h1>Results</h1>
<table border="1" width="500" align="center">
  <tr bgcolor="00FF7F">
    <th><b>Addition operation</b></th>
    <th><b>Subtraction operation</b></th>
    <th><b>Multiplication operation</b></th>
    <th><b>division operation</b></th>
  </tr>
  <%
    ArrayList<String> results = (ArrayList<String>) request.getAttribute("data");
    for (int i = 0; i < results.size(); i++);
  %>
  <tr>
    <td><%=results.get(0)%></td>
    <td><%=results.get(1)%></td>
    <td><%=results.get(2)%></td>
    <td><%=results.get(3)%></td>
  </tr>
</table>
<hr/>
</body>
</html>
