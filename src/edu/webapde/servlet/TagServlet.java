package edu.webapde.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.webapde.bean.Tag;
import edu.webapde.service.TagService;

/**
 * Servlet implementation class TagServlet
 */
@WebServlet(urlPatterns={"/ajaxtags/*"})
public class TagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagServlet() {
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
		case "/ajaxtags" :
			getTagsOfPhoto(request, response);
			break;
		}
	}

	private void getTagsOfPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String decoded = URLDecoder.decode(url, "UTF-8");
		int id = Integer.parseInt(decoded);
		
		String strTags = "";
		List<Tag> tags = TagService.getTagsOfPhoto(id);
		for (Tag tag : tags)
			strTags += ", " + tag.getTagname();
		if (!strTags.equals(""))
			strTags = strTags.substring(2);
		
		response.getWriter().write(strTags);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
