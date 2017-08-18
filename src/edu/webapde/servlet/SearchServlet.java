package edu.webapde.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.webapde.bean.Photo;
import edu.webapde.bean.Tag;
import edu.webapde.bean.User;
import edu.webapde.service.PhotoService;
import edu.webapde.service.SharedService;
import edu.webapde.service.TagService;
import edu.webapde.service.UserService;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(urlPatterns={"/search", "/ajaxsearchpublic/*", "/ajaxsearchprivate/*"})
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
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
		case "/search" :
			searchPhotos(request, response);
			break;
		case "/ajaxsearchpublic" :
			searchPublicPhotos(request, response);
			break;
		case "/ajaxsearchprivate" :
			searchPrivatePhotos(request, response);
			break;
		}
	}

	private void searchPublicPhotos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String key = URLDecoder.decode(url, "UTF-8");
		Gson g = new Gson();
		
		//User user = null;
		//String username = (String) request.getSession().getAttribute("un");
		
		List<Photo> photos = new ArrayList<Photo>();
		List<Photo> publicphotos = PhotoService.getPhotos(false);
		for (Photo photo : publicphotos) {
			List<Tag> tags = TagService.getTagsOfPhoto(photo.getId());
			for (Tag tag : tags) {
				if (tag.getTagname().toLowerCase().equals(key)) {
					photos.add(photo);
					break;
				}
			}
		}
		Collections.reverse(photos);
		String jsonString = g.toJson(photos);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}

	private void searchPrivatePhotos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String key = URLDecoder.decode(url, "UTF-8");
		Gson g = new Gson();
		
		User user = null;
		String username = (String) request.getSession().getAttribute("un");
		
		if (username != null && !username.equals("")) {
			user = UserService.getUserByUsername(username);
			List<Photo> photos = new ArrayList<Photo>();
			List<Photo> privatephotos = PhotoService.getPhotos(true);
			for (Photo photo : privatephotos) {
				if (SharedService.isSharedWith(photo.getId(), user.getId())) {
					List<Tag> tags = TagService.getTagsOfPhoto(photo.getId());
					for (Tag tag : tags) {
						if (tag.getTagname().toLowerCase().equals(key)) {
							photos.add(photo);
							break;
						}
					}
				}
			}
			Collections.reverse(photos);
			String jsonString = g.toJson(photos);
			response.setContentType("application/json");
			response.getWriter().write(jsonString);
		} else {
			List<Photo> empty = new ArrayList<Photo>();
			String jsonString = g.toJson(empty);
			response.setContentType("application/json");
			response.getWriter().write(jsonString);
		}
	}
	
	private void searchPhotos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String key = request.getParameter("key");
		
		if (key.equals("")) {
			request.getRequestDispatcher("photos").forward(request, response);
		} else {
			key = key.toLowerCase();
			request.setAttribute("searchkey", key);
			request.getRequestDispatcher("photos").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
