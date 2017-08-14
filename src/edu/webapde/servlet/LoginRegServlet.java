package edu.webapde.servlet;

import java.util.List;
import java.io.IOException;

import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.webapde.bean.User;
import edu.webapde.service.UserService;

/**
 * Servlet implementation class LoginRegServlet
 */
@WebServlet(urlPatterns={"/register", "/login", "/relog"})
public class LoginRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginRegServlet() {
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
		case "/register" :
			RegisterUser(request, response);
			break;
		case "/login" : 
			LoginUser(request, response);
			break;
		case "/relog" :
			
			break;
		}
	}

	private void RegisterUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String username = request.getParameter("uname");
		String password = request.getParameter("pword");
		String desc = request.getParameter("desc");
		
		if (username.equals("") || password.equals("")) {
			request.setAttribute("regerror", "Please fill out username and password fields");
			request.getRequestDispatcher("loginreg.jsp").forward(request, response);
		} else {
			List<User> users = UserService.getAllUsers();
			boolean found = false;
			
			if (users != null) {
				for (User user : users) {
					if (user.getUsername().equals(username)) {
						found = true;
						break;
					}
				}
			}
			
			if (!found) {
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setDesc(desc);
				UserService.addUser(user);
				
				//cookie stuff
				
				response.sendRedirect("");
			} else {
				request.setAttribute("regerror", "Username already taken!");
				request.getRequestDispatcher("loginreg.jsp").forward(request, response);
			}
		}
	}

	private void LoginUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
