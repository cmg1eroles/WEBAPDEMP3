package edu.webapde.service;

import javax.persistence.*;
import java.util.List;
import edu.webapde.bean.User;

public class UserService {
	
	public static void addUser(User user) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			em.persist(user);
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
	}
	
	public static User getUser(int id) {
		User user = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		trans.begin();
		user = em.find(User.class, id);
		trans.commit();
		
		em.close();
		
		return user;
	}
	
	public static List<User> getAllUsers() {
		List<User> users = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<User> query = em.createQuery("SELECT user FROM user user", User.class);
			users = query.getResultList();
			
			trans.commit();
		}catch(Exception e){
			if(trans!=null){
				trans.rollback();
			}
			e.printStackTrace();
		}
		em.close();
		
		return users;
	}
	
	/*public static boolean updateUser(int id, User user) {
		
	}*/
}
