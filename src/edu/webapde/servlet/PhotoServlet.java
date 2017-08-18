package edu.webapde.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import edu.webapde.bean.Photo;
import edu.webapde.bean.Shared;
import edu.webapde.bean.Tag;
import edu.webapde.bean.User;
import edu.webapde.service.PhotoService;
import edu.webapde.service.SharedService;
import edu.webapde.service.TagService;
import edu.webapde.service.UserService;

/**
 * Servlet implementation class PhotoServlet
 */
@WebServlet(urlPatterns={"/photos", "/ajaxphotos/*", "/ajaxuserphotos/*", "/photo/*", "/uploadphoto", "/editphoto", "/ajaxphoto/*", "/updatephoto"})
@MultipartConfig
public class PhotoServlet extends HttpServlet {
	
	public static File FOLDER = new File("/Users/Carlo Eroles/Documents/WEBAPDE/MP3Photos");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoServlet() {
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
		case "/photos" :
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
			break;
		case "/ajaxphotos" :
			getPhotos(request, response);
			break;
		case "/ajaxuserphotos" :
			getUserPhotos(request, response);
			break;
		case "/photo" :
			loadPhoto(request, response);
			break;
		case "/uploadphoto" :
			uploadPhoto(request, response);
			break;
		case "/editphoto" :
			String id = request.getParameter("id");
			request.setAttribute("photoId", id);
			request.getRequestDispatcher("editphoto.jsp").forward(request, response);
			break;
		case "/ajaxphoto" :
			getPhoto(request, response);
			break;
		case "/updatephoto" :
			updatePhoto(request, response);
			break;
		}
	}

	

	private void updatePhoto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id = Integer.parseInt(request.getParameter("photoId"));
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String strTags = request.getParameter("tag");
		String[] tags = strTags.split(", ");
		
		boolean updated = false;
		
		if (!title.equals("") && !desc.equals("")) {
			Photo photo = PhotoService.getPhoto(id);
			photo.setTitle(title);
			photo.setDesc(desc);
			updated = PhotoService.updatePhoto(id, photo);
			
			TagService.removeTagsOfPhoto(id);
			if (!strTags.equals("")) {
				for (int i = 0 ; i < tags.length ; i++) {
					Tag tag = new Tag();
					tag.setPhotoid(id);
					tag.setTagname(tags[i]);
					TagService.addTag(tag);
				}
			}
			
			if (photo.isPrivacy()) {
				String strShare = request.getParameter("share");
				String[] share = strShare.split(", ");
				SharedService.removeSharedWith(id);
				if (!strShare.equals("")) {
					for (int i = 0 ; i < share.length ; i++) {
						Shared shared = new Shared();
						shared.setPhotoid(id);
						shared.setUserid(UserService.getUserByUsername(share[i]).getId());
						SharedService.addShared(shared);
					}
				}
			}
		}
		
		if (updated)
			request.setAttribute("success", "Successfully updated!");
		else request.setAttribute("error", "Error in updating!");
		request.getRequestDispatcher("editphoto?id="+id).forward(request, response);
	}

	private void getPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String decoded = URLDecoder.decode(url, "UTF-8");
		int id = Integer.parseInt(decoded);
		Gson g = new Gson();
		
		Photo photo = PhotoService.getPhoto(id);
		String jsonString = g.toJson(photo);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}

	private void loadPhoto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String filename = URLDecoder.decode(url, "UTF-8");
		
		File file = new File(FOLDER.getPath(), filename);
		response.setHeader("Content-Type", getServletContext().getMimeType(filename));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		
		Files.copy(file.toPath(), response.getOutputStream());
	}

	private void getPhotos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String type = URLDecoder.decode(url, "UTF-8");
		Gson g = new Gson();
		
		User user = null;
		String username = (String) request.getSession().getAttribute("un");
		
		if (type.equals("public")) {
			List<Photo> publicphotos = PhotoService.getPhotos(false);
			Collections.reverse(publicphotos);
			String jsonString = g.toJson(publicphotos);
			response.setContentType("application/json");
			response.getWriter().write(jsonString);
			
		} else if (type.equals("private") && username != null && !username.equals("")) {
			user = UserService.getUserByUsername(username);
			List<Photo> sharedphotos = PhotoService.getPhotos(true);
			List<Photo> privatephotos = new ArrayList<Photo>();
			for (Photo photo : sharedphotos) {
				if (SharedService.isSharedWith(photo.getId(), user.getId()))
					privatephotos.add(photo);
			}
			Collections.reverse(privatephotos);
			String jsonString = g.toJson(privatephotos);
			response.setContentType("application/json");
			response.getWriter().write(jsonString);
		} else {
			List<Photo> empty = new ArrayList<Photo>();
			String jsonString = g.toJson(empty);
			response.setContentType("application/json");
			response.getWriter().write(jsonString);
		}
	}
	
	private void getUserPhotos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String url = request.getPathInfo().substring(1);
		String username = URLDecoder.decode(url, "UTF-8");
		String loggedin = (String) request.getSession().getAttribute("un");
		Gson g = new Gson();
		
		User user = UserService.getUserByUsername(username);
		User loggedinuser = UserService.getUserByUsername(loggedin);
		
		List<Photo> photos = new ArrayList<Photo>();
		List<Photo> publicphotos = PhotoService.getPhotos(false);
		List<Photo> privatephotos = PhotoService.getPhotos(true);
		
		for (Photo photo : publicphotos) {
			if (photo.getUserid() == user.getId())
				photos.add(photo);
		}
		
		if (loggedinuser != null) {
			for (Photo photo : privatephotos) {
				if ((photo.getUserid() == user.getId() && SharedService.isSharedWith(photo.getId(), loggedinuser.getId())) ||
					photo.getUserid() == loggedinuser.getId())
					photos.add(photo);
			}
		}
		Collections.reverse(photos);
		String jsonString = g.toJson(photos);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}
	
	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Part part = request.getPart("image");
		String fname = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		String fileName = System.currentTimeMillis() + "-image.jpg";
		String username = (String) request.getSession().getAttribute("un");
		
		User user = UserService.getUserByUsername(username);
		
		int userid = user.getId();
		String title = request.getParameter("title");
		String desc = request.getParameter("descr");
		String priv = request.getParameter("btn");
		String strTags = request.getParameter("tag");
		boolean privacy = false;
		if (priv.equals("public")) {
			privacy = false;
		} else if (priv.equals("private")) {
			privacy = true;
		}
		
		if (!fname.equals("") && !title.equals("") && !desc.equals("")) {
			File img = new File(FOLDER, fileName);
			
			InputStream fileInputStream = part.getInputStream();
			Files.copy(fileInputStream, img.toPath(), StandardCopyOption.REPLACE_EXISTING);
			fileInputStream.close();
			
			
			Photo photo = new Photo();
			photo.setUserid(userid);
			photo.setTitle(title);
			photo.setDesc(desc);
			photo.setPrivacy(privacy);
			photo.setFilename(fileName);
			
			PhotoService.addPhoto(photo);
			
			if (!strTags.equals("")) {
				String[] strs = strTags.split(", ");
				for (int i = 0 ; i < strs.length ; i++) {
					System.out.println(strs[i]);
					Tag tag = new Tag();
					tag.setPhotoid(photo.getId());
					tag.setTagname(strs[i]);
					TagService.addTag(tag);
				}
			}
			
			//reload upload page
			request.setAttribute("success", fname + " successfully uploaded!");
		} else {
			request.setAttribute("error", "ERROR: Could not upload image!");
		}
		
		request.getRequestDispatcher("uploadpage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
