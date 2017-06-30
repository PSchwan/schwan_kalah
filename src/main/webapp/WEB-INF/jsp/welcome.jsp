<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <spring:url value="/css/main.css" var="springCss"/>
    <c:url value="/css/main.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>
</head>

<body>
<%@include file="includes/navigation.jsp" %>

<div class="container">

    <div class="starter-template">
        <h3>A new game of Kalah has just been started</h3>
        <p><a href="/game">Click here to start the game</a></p>
    </div>

</div>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>