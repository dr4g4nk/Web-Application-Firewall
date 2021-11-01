<%@page import="org.unibl.etf.dao.DAOUser"%>
<%@page import="org.unibl.etf.dto.DTOItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="script.js"></script>
<title>Aplikacija za upravljanje artiklima</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script type="text/javascript" src="script.js"></script>
</head>
<%
	String username = (String) session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
	int role = DAOUser.getRoleFromUser(username);
	if (role == 0) {
		response.sendRedirect("errorPage.html");
	}
%>
<body onload="javascript:getItems()">
<button id="logout" onclick="javascript:logout()">Logout</button>
<form action="./items" method="POST">
	<div>
		<label>Naziv proizvoda *</label>
		<input type="text" name="name" id="name" required="required">
	</div>
	<div>
		<label>Opis proizvoda</label>
		<input type="text" name="description" id="description">
	</div>
	<div>
		<label>Cijena *</label>
		<input type="number" name="price" id="price" step="0.01">
	</div>
	<input type="submit" value="Dodaj proizvod">
</form>
<br><br><br>
<div id="table"></div>
</body>
</html>