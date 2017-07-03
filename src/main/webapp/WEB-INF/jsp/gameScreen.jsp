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

    <div>

        <c:if test="${gameOver}">
            <h1 class="won">Game Over</h1>
            <c:choose>
                <c:when test="${draw}">
                    <h1 class="won">It ended a draw</h1>
                </c:when>
                <c:otherwise>
                    <h1 class="won">Congratulations ${winner.name}!</h1>
                </c:otherwise>
            </c:choose>
        </c:if>

        <h2>Board:</h2>
        <div class="board">
            <table class="board">
                <tr>
                    <c:choose>
                        <c:when test="${player1sTurn}">
                            <form action="/move" method="post">
                                <td></td>
                                <c:forEach items="${Player1Pits}" var="pit" varStatus="index">
                                    <td class="pit player1">
                                        <button type="submit" id="player1:${index.index}" name="move" class="player1"
                                                value="${index.index}">${pit}</button>
                                    </td>
                                </c:forEach>
                                <td></td>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                            <c:forEach items="${Player1Pits}" var="pit" varStatus="index">
                                <td class="pit player1">
                                        ${pit}
                                </td>
                            </c:forEach>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td class="kalah player1">${Player1Kalah}</td>
                    <c:forEach items="${Player1Pits}"
                               var="pit1"> <!-- Not actually using the pits, just want the empty cells...-->
                        <td></td>
                    </c:forEach>
                    <td class="kalah player2">${Player2Kalah}</td>
                </tr>
                <tr>
                    <c:choose>
                        <c:when test="${player2sTurn}">
                            <form action="/move" method="post">
                                <td></td>
                                <c:forEach items="${Player2Pits}" var="pit" varStatus="index">
                                    <td class="pit player2">
                                        <button type="submit" id="player2:${index.index}" name="move" class="player2"
                                                value="${index.index}">${pit}</button>
                                    </td>
                                </c:forEach>
                                <td></td>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                            <c:forEach items="${Player2Pits}" var="pit" varStatus="index">
                                <td class="pit player2">
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

</div>

<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>

</html>