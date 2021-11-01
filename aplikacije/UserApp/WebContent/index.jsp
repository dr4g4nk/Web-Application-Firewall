<%@page import="edu.yale.its.tp.cas.client.filter.CASFilter"%>
<%@page import="org.unibl.etf.dao.DAOUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aplikacija za upravljanje korisnicima</title>
<link rel="stylesheet" type="text/css" href="style.css">
<script type="text/javascript" src="script.js"></script>
</head>
<%
	String username = (String) session.getAttribute(CASFilter.CAS_FILTER_USER);
	int role = DAOUser.getRoleFromUser(username);
	if (role != 2) {
		response.sendRedirect("errorPage.html");
	}
%>
<body onload="javascript:getUsers()">
<button id="logout" onclick="javascript:logout()">Logout</button>
<form action="./users" method="POST">
	<div>
		<label>Korisnicko ime *</label>
		<input type="text" name="username" id="username" required="required">
	</div>
	<div>
		<label>Lozinka *</label>
		<input type="password" name="password" id="password" required="required">
	</div>
	<div>
		<label>Uloga</label><br>
		<input type="radio" name="role" id="role" value="Admin"> Admin <br>
		<input type="radio" name="role" id="role" value="Admin artikal">Admin artikal<br>
		<input type="radio" name="role" id="role" value="kupac" checked="checked"> Kupac <br>
	</div>
	<input type="submit" value="Dodaj korisnika">
</form>
<br><br><br>
<div id="table"></div>
</body>
</html>