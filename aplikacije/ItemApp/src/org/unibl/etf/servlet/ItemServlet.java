package org.unibl.etf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.unibl.etf.dao.DAOItem;
import org.unibl.etf.dao.DAOUser;
import org.unibl.etf.dto.DTOItem;

import com.google.gson.Gson;

import edu.yale.its.tp.cas.client.filter.CASFilter;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/items")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ItemServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (checkUser(request)) {
			response.sendRedirect("errorPage.html");
		} else {
			PrintWriter writer = response.getWriter();
			String gson = new Gson().toJson(DAOItem.listOfArticle());
			writer.print(gson);
			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (checkUser(request)) {
			response.sendRedirect("errorPage.html");
		} else {
			try {
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				double price = Double.parseDouble(request.getParameter("price"));
				if (name != null && description != null && !name.isEmpty()) {
					DTOItem item = new DTOItem(0, name, description, price);
					DAOItem.insertItem(item);
				}
				response.sendRedirect("index.jsp");
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}

		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (checkUser(request)) {
			response.sendRedirect("errorPage.html");
		} else {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				DAOItem.deleteItem(id);
				response.setStatus(200);
			} catch (NumberFormatException e) {
				// TODO: handle exception
			}
		}

	}

	protected boolean checkUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(CASFilter.CAS_FILTER_USER);
		return DAOUser.getRoleFromUser(username) == 0;
	}
}
