package edu.sjsu.cmpe275.lab2.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.sjsu.cmpe275.lab2.model.Book;
import edu.sjsu.cmpe275.lab2.model.User;

public class BookCheckout {
	
	LocalDateTime timePoint = LocalDateTime.now();
	LocalDate theDate = timePoint.toLocalDate();

	public Book getbDetails(String bookid) {
		// TODO Auto-generated method stub
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "275_lab2" );
	    EntityManager entitymanager = emfactory.createEntityManager( );
	    entitymanager.getTransaction( ).begin( );
		Book book = new Book();  
	    book = entitymanager.find(Book.class, bookid);	
	    if(book==null)
	    	return null;
	    entitymanager.persist( book );
	    entitymanager.getTransaction( ).commit( );
	    entitymanager.close( );
	    emfactory.close( );
	    return book;

	}

	public String checkoutBook(String bookid) {
		// TODO Auto-generated method stub
		String dueDateConversion = null;
		User user = new User(); //replace with session
		LocalDateTime timePoint = LocalDateTime.now();
		LocalDate theDate = timePoint.toLocalDate();
		String currentDate = theDate.toString();
		int tolalNumberOfBooksUserHave = user.getBooks().size();
		EntityManagerFactory emfactory =  Persistence.createEntityManagerFactory( "275_lab2" );
		EntityManager entityManager = emfactory.createEntityManager( );
		entityManager.getTransaction( ).begin( );
		//need to check the user id in the query
		String numberOfBooksUserCheckedoutInADay =  entityManager.createQuery("SELECT COUNT(u.checkoutDate) FROM User u WHERE u.checkoutDate ='"+ currentDate +"'"+"  GROUP BY u.checkoutDate").getSingleResult().toString();
		entityManager.getTransaction( ).commit( );
	    entityManager.close( );
	    emfactory.close( );
		if(tolalNumberOfBooksUserHave == 10)
		{
			return ("you cannot checkout any more books, until you return atleast one book back to the library");
		}
		else if(numberOfBooksUserCheckedoutInADay == "5")
		{
			return ("you cannot checkout any more books for today. Please do checkout tomorrow");
		}
		else
		{
			//get the object of the user from userid and then do the following
			//add the book id to the user<book> list
			user.setCheckoutDate(currentDate);
			LocalDate dueDate = timePoint.toLocalDate().plusDays(30);
			dueDateConversion = dueDate.toString();
			user.setDueDate(dueDateConversion);
		}
		/*return ("Book Checkedout. your due date is" + dueDateConversion +" ");*/ //imp
		return null;
	}

	public String addBooksToPatron(String userid, String bookid) {
		
		
		// TODO Auto-generated method stub
		
		
		String dueDateConversion = null;
		//User user = new User(); //replace with session
		LocalDateTime timePoint = LocalDateTime.now();
		LocalDate theDate = timePoint.toLocalDate();
		String currentDate = theDate.toString();
		
		EntityManagerFactory emfactory =  Persistence.createEntityManagerFactory( "275_lab2" );
		EntityManager entityManager = emfactory.createEntityManager( );
		entityManager.getTransaction( ).begin( );
		User user = entityManager.find(User.class, userid);
		/*if(user.getBooks().size()!=){
			
		}*/
		//int tolalNumberOfBooksUserHave = user.getBooks().size();
		//need to check the user id in the query
		String numberOfBooksUserCheckedoutInADay =  entityManager.createQuery("SELECT COUNT(u.checkoutDate) FROM User u WHERE u.checkoutDate ='"+ currentDate +"'"+" and u.userid ='"+ userid +"'"+" GROUP BY u.checkoutDate").getSingleResult().toString();
		entityManager.getTransaction( ).commit( );
	    entityManager.close( );
	    emfactory.close( );
		/*if(tolalNumberOfBooksUserHave == 10)
		{
			return "bookLimitReached";
			//return ("you cannot checkout any more books, until you return atleast one book back to the library");
		}*/
		/*else if(numberOfBooksUserCheckedoutInADay == "5")
		{
			return "bookLimitReachedForTheDay";
			//return ("you cannot checkout any more books for today. Please do checkout tomorrow");
		}
		else
		{*/
			//get the object of the user from userid and then do the following
			//add the book id to the user<book> list
			user.setCheckoutDate(currentDate);
			LocalDate dueDate = timePoint.toLocalDate().plusDays(30);
			dueDateConversion = dueDate.toString();
			user.setDueDate(dueDateConversion);
			return null;
		/*}*/
		/*return ("Book Checkedout. your due date is" + dueDateConversion +" ");*/ //imp
		
	}


	/*public String addBooks(List<String> listOfBooksForCurrentCheckout) {
		// TODO Auto-generated method stub
		String dueDateConversion = null;
		LocalDateTime timePoint = LocalDateTime.now();
		LocalDate theDate = timePoint.toLocalDate();
		LocalDate dueDate = timePoint.toLocalDate().plusDays(30);
		dueDateConversion = dueDate.toString();
		StringBuilder sB = new StringBuilder();
		
		for(String l : listOfBooksForCurrentCheckout)
		{
			sB.append(l);
		}
		sB.append(dueDateConversion);
		
		return sB.toString();
	}*/

}