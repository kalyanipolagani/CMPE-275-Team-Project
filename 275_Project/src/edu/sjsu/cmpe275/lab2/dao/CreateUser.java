package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import edu.sjsu.cmpe275.lab2.model.Book;
import edu.sjsu.cmpe275.lab2.model.User;
import edu.sjsu.cmpe275.lab2.model.Librarian;

import org.json.simple.*;

public class CreateUser {

	public String insertUser(String firstName, String lastName, String email, String password, String univid,
			String uniquecode) {
		// TODO Auto-generated method stub
		// entitymanager instance associated with persistence context
		// This method is called by User Controller for inserting User Details
		// into Database
		try{
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("275_lab2");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		

		User user = new User();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setUnivid(univid);
		user.setUniquecode(uniquecode);

		

		entitymanager.persist(user);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return "Success";
		}catch(RollbackException e) {
	        return"Failure";
	    }
	}

	
	
	public User getObjectByEmail(String email) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("275_lab2");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		User user = new User();
		String userid = null;
		try{
		userid = entitymanager.createQuery("SELECT u.userid FROM User u WHERE u.email ='" + email + "'")
				.getSingleResult().toString();
		
		if (userid == null)
			return null;
		user = entitymanager.find(User.class, userid);
		if (user == null)
			return null;
		entitymanager.persist(user);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();

		return user;
		}catch(NoResultException e) {
	        return null;
	    }
	}

	public User getObjectById(String userid) {

		// This method is called by User Controller for getting User Details
		// from Database

		try{
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("275_lab2");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		User user = new User();

		user = entitymanager.find(User.class, userid);
		if (user == null)
			return null;
		entitymanager.persist(user);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		return user;
		
		}catch(NoResultException e) {
	        return null;
	    }

	}

	public void update(String firstName, String lastName, String email, String password, String univid, String userId) {
		// TODO Auto-generated method stub

		// This method is called by User Controller for Updating User Details
		// into Database
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("275_lab2");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		User user = new User();
		user = entitymanager.find(User.class, userId);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		user.setUnivid(univid);

		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}

	// public void deleteObjectById(String userId) {
	// // TODO Auto-generated method stub
	// This method is called by User Controller for Deleting User Details from
	// Database
	// EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
	// "275_lab2" );
	// EntityManager entitymanager = emfactory.createEntityManager( );
	// entitymanager.getTransaction( ).begin( );
	//
	// User user = new User();
	//
	// user = entitymanager.find(User.class, userId);
	//
	// List<Phone> phone = user.getPhones();
	// for(Phone p : phone)
	// {
	// Phone pId = entitymanager.find(Phone.class, p.getId());
	// pId.getUser().remove(user);
	// }
	//
	// //address = entitymanager.find(Address.class);
	//
	//// entitymanager.getTransaction().begin();
	// entitymanager.remove(user);
	// //entitymanager.remove(address);
	// entitymanager.getTransaction().commit();
	// entitymanager.close();
	//
	// }
	//
	// public User getJsonById(String userid) {
	// // TODO Auto-generated method stub
	// System.out.println("i am in dao");
	// EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(
	// "275_lab2" );
	// EntityManager entitymanager = emfactory.createEntityManager( );
	// entitymanager.getTransaction( ).begin( );
	//
	// System.out.println("user id"+userid);
	// User user = entitymanager.find(User.class, userid);
	// System.out.println("user object"+ user);
	// entitymanager.persist( user );
	// entitymanager.getTransaction( ).commit( );
	// entitymanager.close( );
	// emfactory.close( );
	// return user;
	//
	// }
}
