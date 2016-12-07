package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import edu.sjsu.cmpe275.lab2.model.Book;
import edu.sjsu.cmpe275.lab2.model.User;
import edu.sjsu.cmpe275.lab2.model.Librarian;

import org.json.simple.*;

public class CreateLibrarian {

	
	public void insertLibrarian(String firstName, String lastName, String email, String password, String univid,
			String uniquecode) {
		// TODO Auto-generated method stub
		// entitymanager instance associated with persistence context
		// This method is called by User Controller for inserting User Details
		// into Database
		System.out.println("cufn1" + firstName);
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("275_lab2");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();
		System.out.println("cufn2c" + lastName);

		Librarian lib = new Librarian();

		lib.setFirstName(firstName);
		lib.setLastName(lastName);
		lib.setEmail(email);
		lib.setPassword(password);
		lib.setUnivid(univid);
		lib.setUniquecode(uniquecode);

		System.out.println("cufn" + email);

		entitymanager.persist(lib);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
	}
	
	
	public Librarian getLibObjectByEmail(String email) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("275_lab2");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Librarian lib = new Librarian();
		String userid = null;
		try{
		userid = entitymanager.createQuery("SELECT u.userid FROM Librarian u WHERE u.email ='" + email + "'")
				.getSingleResult().toString();
		System.out.println(userid+"libid");
		if (userid == null)
			return null;
		lib = entitymanager.find(Librarian.class, userid);
		System.out.println("lib obj"+lib.getEmail());
		if (lib == null)
			return null;
		entitymanager.persist(lib);
		entitymanager.getTransaction().commit();
		entitymanager.close();
		emfactory.close();
		System.out.println("lib obj2"+lib.getEmail());
		return lib;
		}catch(NoResultException e) {
	        return null;
	    }
	}

	public User getObjectById(String userid) {

		// This method is called by User Controller for getting User Details
		// from Database

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


}
