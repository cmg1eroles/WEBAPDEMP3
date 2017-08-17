package edu.webapde.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import edu.webapde.bean.Photo;
import edu.webapde.bean.User;
import edu.webapde.service.PhotoService;
import edu.webapde.service.SharedService;
import edu.webapde.service.UserService;

/**
 * Servlet implementation class PhotoServlet
 */
@WebServlet(urlPatterns={"/photos", "/photo/*", "/uploadphoto"})
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
			getAllPhotos(request, response);
			break;
		case "/photo" :
			loadPhoto(request, response);
			break;
		case "/uploadphoto" :
			uploadPhoto(request, response);
			break;
		}
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

	private void getAllPhotos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = null;
		String username = (String) request.getSession().getAttribute("un");
		
		List<Photo> publicphotos = PhotoService.getPhotos(false);
		Collections.reverse(publicphotos);
		request.setAttribute("publicphotos", publicphotos);
		
		if (username != null && !username.equals("")) {
			user = UserService.getUserByUsername(username);
			List<Photo> sharedphotos = PhotoService.getPhotos(true);
			List<Photo> privatephotos = new ArrayList<Photo>();
			for (Photo photo : sharedphotos) {
				if (SharedService.isSharedWith(photo.getId(), user.getId()))
					privatephotos.add(photo);
			}
			Collections.reverse(privatephotos);
			request.setAttribute("privatephotos", privatephotos);
		}
		
		request.getRequestDispatcher("homepage.jsp").forward(request, response);
	}

	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Part part = request.getPart("image");
		String fname = Paths.get(part.getSubmittedFileName()).getFileName().toString();
		String fileName = System.currentTimeMillis() + "-image.jpg";
		
		//String user = request.getParameter("userid");
		String title = request.getParameter("title");
		String desc = request.getParameter("descr");
		//String priv = request.getParameter("privacy");
		
		File img = new File(FOLDER, fileName);
		
		InputStream fileInputStream = part.getInputStream();
		Files.copy(fileInputStream, img.toPath(), StandardCopyOption.REPLACE_EXISTING);
		fileInputStream.close();
		
		//save to db
		/*Photo photo = new Photo();
		photo.setUserid(user);
		photo.setTitle(title);
		photo.setDesc(desc);
		photo.setPrivacy(priv);
		photo.setFilename(fileName);
		
		PhotoService.addPhoto(photo);*/
		
		//reload upload page
		request.setAttribute("success", fname + " successfully uploaded!");
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
