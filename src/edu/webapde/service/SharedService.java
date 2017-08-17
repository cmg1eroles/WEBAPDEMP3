package edu.webapde.service;

import java.util.List;

import javax.persistence.*;

import edu.webapde.bean.Shared;

public class SharedService {
	
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
			
			if (shared.size() > 0) {
				isShared = true;
				System.out.println("\n\n\nIt's shared with youuuu!\n\n");
			}
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return isShared;
	}
}
