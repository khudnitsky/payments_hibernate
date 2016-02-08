<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Операции</title>
	</head>
	<body>
		<table border="1">
			<tr bgcolor="#CCCCCC">
				<td align="center"><strong>Дата</strong></td>
				<td align="center"><strong>Описание</strong></td>
				<td align="center"><strong>Сумма</strong></td>
				<td align="center"><strong>№ Клиента</strong></td>
				<td align="center"><strong>№ Счета</strong></td>
			</tr>
			<%--<jsp:useBean id="operation" scope="request" type="by.pvt.khudnitsky.payments.entities.Operation"/>--%>
			<%--${operation.date.toString()}--%>
			<c:forEach var="opeartion" items="${operationsList}">
				<tr>
					<td><c:out value="${ operation.date.toString() }" /></td>
					<td><c:out value="${ opeartion.description }" /></td>
					<td align="right">
						<fmt:formatNumber value="${ opeartion.amount }" type="currency" minFractionDigits="2"  currencySymbol=""/>
					</td>
					<td align="center"><c:out value="${ opeartion.user.lastName }" /></td>
					<td align="center"><c:out value="${ opeartion.account.accountNumber }" /></td>
				</tr>
			</c:forEach>
		</table>

		<%--For displaying Previous link except for the 1st page --%>
		<c:if test="${currentPage != 1}">
			<td><a href="controller?command=operations&page=${currentPage - 1}">Предыдущая</a></td>
		</c:if>

		<%--For displaying Page numbers.
        The when condition does not display a link for the current page--%>
		<table border="1" cellpadding="5" cellspacing="5">
			<tr>
				<c:forEach begin="1" end="${numberOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<td>${i}</td>
						</c:when>
						<c:otherwise>
							<td><a href="controller?command=operations&page=${i}">${i}</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</tr>
		</table>

		<%--For displaying Next link --%>
		<c:if test="${currentPage lt numberOfPages}">
			<td><a href="controller?command=operations&page=${currentPage + 1}">Next</a></td>
		</c:if>

		<br>
		<a href="controller?command=backadmin">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
	</body>
</html>