package edu.webapde.service;

import javax.persistence.*;
import java.util.List;

import edu.webapde.bean.Photo;

public class PhotoService {
	
	public static void addPhoto(Photo photo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			em.persist(photo);
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
	}
	
	public static Photo getPhoto(int id) {
		Photo photo = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		trans.begin();
		photo = em.find(Photo.class, id);
		trans.commit();
		
		em.close();
		
		return photo;
	}
	
	public static List<Photo> getPhotos(boolean privacy) {
		List<Photo> photos = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Photo> query = em.createQuery("SELECT photo FROM photo photo WHERE privacy = :privacy", Photo.class);
			query.setParameter("privacy", privacy);
			photos = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return photos;
	}
	
	public static boolean updatePhoto(int id, Photo newPhoto) {
		boolean success = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			Photo p = em.find(Photo.class, id);
			p.setTitle(newPhoto.getTitle());
			p.setDesc(newPhoto.getDesc());
			p.setPrivacy(newPhoto.isPrivacy());
			
			trans.commit();
			
			success = true;
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return success;
	}
}
