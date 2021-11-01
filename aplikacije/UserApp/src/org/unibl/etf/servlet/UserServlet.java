package org.unibl.etf.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.unibl.etf.dao.DAOUser;
import org.unibl.etf.dto.DTOUser;

import com.google.gson.Gson;

import edu.yale.its.tp.cas.client.filter.CASFilter;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/users")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
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
			String gson = new Gson().toJson(DAOUser.listOfUser());
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
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String role = request.getParameter("role");
			if (username != null && password != null && role != null && !username.isEmpty() && !password.isEmpty()
					&& !role.isEmpty()) {
				DTOUser user = new DTOUser(username, password,
						role.equals("Admin") ? 2 : role.equals("Admin artikal") ? 1 : 0);
				DAOUser.insertUser(user);
			}
			response.sendRedirect("index.jsp");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (checkUser(request)) {
			response.sendRedirect("errorPage.html");
		} else {
			try {
				String username = request.getParameter("username");
				DAOUser.deleteUser(username);
				response.setStatus(200);
			} catch (Exception e) {
				
			}
		}

	}

	protected boolean checkUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(CASFilter.CAS_FILTER_USER);
		return DAOUser.getRoleFromUser(username) == 0;
	}
}
