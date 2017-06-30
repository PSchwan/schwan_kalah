<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

    <!-- Access the bootstrap Css like this,
        Spring boot will handle the resource mapping automcatically -->
    <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css"/>

    <!--
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	 -->
    <c:url value="/css/main.css" var="jstlCss"/>
    <link href="${jstlCss}" rel="stylesheet"/>

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Schwan Kalah</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/">Home</a></li>
                <li><a href="/game">Game</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">

    <div class="starter-template">
        <h1>Schwan Kalah Game</h1>

        <h2>Board:</h2>
        <table>
            <tr>
                <c:choose>
                    <c:when test="${player1sTurn}">
                        <form action="/move" method="post">
                            <td></td>
                            <c:forEach items="${Player1Pits}" var="pit" varStatus="index">
                                <td>
                                    <button type="submit" id="player1:${index.index}" name="move"
                                            value="${index.index}">${pit}</button>
                                </td>
                            </c:forEach>
                            <td></td>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                        <c:forEach items="${Player1Pits}" var="pit" varStatus="index">
                            <td>
                                    ${pit}
                            </td>
                        </c:forEach>
                        <td></td>
                    </c:otherwise>
                </c:choose>
            </tr>
            <tr>
                <td>${Player1Kalah}</td>
                <c:forEach items="${Player1Pits}"
                           var="pit1"> <!-- Not actually using the pits, just want the empty cells...-->
                    <td></td>
                </c:forEach>
                <td>${Player2Kalah}</td>
            </tr>
            <tr>
                <c:choose>
                    <c:when test="${player2sTurn}">
                        <form action="/move" method="post">
                            <td></td>
                            <c:forEach items="${Player2Pits}" var="pit" varStatus="index">
                                <td>
                                    <button type="submit" id="player2:${index.index}" name="move"
                                            value="${index.index}">${pit}</button>
                                </td>
                            </c:forEach>
                            <td></td>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                        <c:forEach items="${Player2Pits}" var="pit" varStatus="index">
                            <td>
                                    ${pit}
                            </td>
                        </c:forEach>
                        <td></td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </table>


    </div>

</div>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>