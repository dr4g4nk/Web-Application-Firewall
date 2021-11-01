package org.unibl.etf.shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import org.unibl.etf.shop.dao.DAOItem;
import org.unibl.etf.shop.dto.DTOItem;

/**
 * Servlet implementation class ShopServlet
 */
@WebServlet("/shop")
public class ShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<DTOItem> list = DAOItem.listOfArticle();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		out.print("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
				"	<title>Aplikaciju za pregled i kupovinu artikala</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"tableStyle.css\">\r\n" +
				"	<script type=\"text/javascript\" src=\"script.js\"></script>\r\n" +
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<button id=\"logout\" onclick=\"javascript:logout()\">Logout</button>"+
				"<h1>Dobrodosli na aplikaciju za pregled i kupovinu\r\n" + 
				"			artikala</h1>\r\n" +
				"<table>\r\n" + 
				"   <caption>Pregled artikala</caption>\r\n" + 
				"   <tr>\r\n" + 
				"     <th>ID</th>\r\n" + 
				"     <th>Naziv</th>\r\n" + 
				"     <th>Opis</th>\r\n" + 
				"     <th>Cijena</th>\r\n" + 
				"     <th>Dodaj u korpu</th>\r\n");
		list.stream().forEach(item -> {
			out.println("<tr>");
			out.println("<td>"+item.getIdItem()+"</td>");
			out.println("<td>"+item.getName()+"</td>");
			out.println("<td>"+item.getDescription()+"</td>");
			out.println("<td>"+item.getPrice()+"</td>");
			out.println("<td><button id=\"" + item.getPrice() + "\" onclick=\"javascript:add(" + item.getPrice()+")\">Dodaj</button></td></tr>");
		});
		out.println("</table><br><br><br>");
		out.println("<div><button onclick=\"javascript:total()\">Zavrsi kupovinu</button></div>");
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
