package edu.webapde.service;

import java.util.List;

import javax.persistence.*;

import edu.webapde.bean.Tag;

public class TagService {
	
	public static void addTag(Tag tag) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			em.persist(tag);
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
	}
	
	public static List<Tag> getAllTags() {
		List<Tag> tags = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Tag> query = em.createQuery("SELECT tag FROM tag tag", Tag.class);
			tags = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return tags;
	}
	
	public static List<Tag> getTagsOfPhoto(int photoid) {
		List<Tag> tags = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Tag> query = em.createQuery("SELECT tag FROM tag tag WHERE photoid = :photoid", Tag.class);
			query.setParameter("photoid", photoid);
			tags = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return tags;
	}
	
	public static void removeTagsOfPhoto(int photoid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Tag> query = em.createQuery("SELECT tag FROM tag tag WHERE photoid = :photoid", Tag.class);
			query.setParameter("photoid", photoid);
			List<Tag> tags = query.getResultList();
			
			for (Tag tag : tags)
				em.remove(tag);
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
	}
}
