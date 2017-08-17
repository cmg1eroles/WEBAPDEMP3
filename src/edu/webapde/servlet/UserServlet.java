package edu.webapde.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.webapde.bean.User;
import edu.webapde.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(urlPatterns={"/ajaxusername/*", "/profile"})
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String path = request.getServletPath();
		
		switch (path) {
		case "/ajaxusername" :
			getUsernameById(request, response);
			break;
		case "/profile" :
			profileRedirect(request, response);
			break;
		}
	}

	private void profileRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("u");
		
		User user = UserService.getUserByUsername(username);
		
		request.setAttribute("user", user);
		
		request.getRequestDispatcher("userprofile.jsp").forward(request, response);
	}

	private void getUsernameById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String decoded = URLDecoder.decode(url, "UTF-8");
		int id = Integer.parseInt(decoded);
		Gson g = new Gson();
		
		String username = UserService.getUsername(id);
		
		String jsonString = g.toJson(username);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
