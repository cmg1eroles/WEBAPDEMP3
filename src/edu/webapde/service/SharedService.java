package edu.webapde.service;

import java.util.List;

import javax.persistence.*;

import edu.webapde.bean.Shared;
import edu.webapde.bean.Tag;

public class SharedService {
	
	public static void addShared(Shared shared) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			em.persist(shared);
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
	}
	
	public static List<Shared> getAllShared() {
		List<Shared> shared = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Shared> query = em.createQuery("SELECT shared FROM shared shared", Shared.class);
			shared = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return shared;
	}
	
	public static List<Shared> getSharedTo(int photoid) {
		List<Shared> shared = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Shared> query = em.createQuery("SELECT shared FROM shared shared WHERE photoid = :photoid", Shared.class);
			query.setParameter("photoid", photoid);
			shared = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return shared;
	}

	public static boolean isSharedWith(int photoid, int userid) {
		boolean isShared = false;
		
		List<Shared> shared = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Shared> query = em.createQuery("SELECT shared FROM shared shared WHERE photoid = :photoid AND userid = :userid", Shared.class);
			query.setParameter("photoid", photoid);
			query.setParameter("userid", userid);
			shared = query.getResultList();
			
			trans.commit();
			
			if (shared.size() > 0)
				isShared = true;
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return isShared;
	}
	
	public static void removeSharedWith(int photoid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Shared> query = em.createQuery("SELECT shared FROM shared shared WHERE photoid = :photoid", Shared.class);
			query.setParameter("photoid", photoid);
			List<Shared> shared = query.getResultList();
			
			for (Shared s : shared)
				em.remove(s);
			
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
