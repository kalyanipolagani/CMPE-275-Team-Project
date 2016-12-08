package edu.sjsu.cmpe275.lab2.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import edu.sjsu.cmpe275.lab2.dao.BookCheckout;
import edu.sjsu.cmpe275.lab2.dao.BookDetails;
import edu.sjsu.cmpe275.lab2.dao.CreateUser;
import edu.sjsu.cmpe275.lab2.email.ActivationEmail;
import edu.sjsu.cmpe275.lab2.model.Book;
//import edu.sjsu.cmpe275.lab2.dao.CreateUser;
import edu.sjsu.cmpe275.lab2.model.User;


@Controller
public class BookCrudController {
	 User user = new User();
	 Book book = new Book();

/*
 * Create book
 */

@RequestMapping(value = "book/createBook", method = RequestMethod.GET )
public ModelAndView createUser(){
	ModelAndView model = new ModelAndView("addBook");
	return model;
}
//Added
@RequestMapping(value = "book/addBookstoPatron", method = RequestMethod.POST )
public ModelAndView addBookstoPatron(@RequestParam("userid") String userid,
									  @RequestParam("bookid") String bookid){
	
	user = new User();
	BookCheckout bC = new BookCheckout();
	CreateUser cu = new CreateUser();
	BookDetails bu = new BookDetails();
	user = cu.getObjectById(userid);
	book = bu.getBookById(bookid);
	LocalDateTime timePoint = LocalDateTime.now();
	String time=timePoint.toString();
	LocalDate theDate = timePoint.toLocalDate();
	String currentDate = theDate.toString();
	LocalDate dueDate = timePoint.toLocalDate().plusDays(30);
	String dueDateConversion = dueDate.toString();
	
	System.out.println("addBookstoPatron"+bookid);
	// bC.addBooksToPatron(userid, bookid);
	User user = bC.getUserById(userid);
	String condition = bC.addBooksToPatron(userid, bookid);
	if (condition == "bookLimitReached")
	{
		ModelAndView model = new ModelAndView("BLR");
		String value = "Book Limit Reached. You cannot checkout any more books, until you return atleast one book back to the library";
		model.addObject("limitReached", value);
		model.addObject(user);
		return model;
	}
	else if(condition.equals("bookLimitReachedForTheDay"))
	{
		ModelAndView model = new ModelAndView("BLR");
		String value = "Book Limit Reached For The Day. You cannot checkout any more books for today. Please do checkout tomorrow";
		model.addObject("limitReached", value);
		model.addObject(user);
		return model;
	}
	else
	{
		ModelAndView model = new ModelAndView("bookCheckoutDetails");
		model.addObject(user);
		model.addObject("dueDate", dueDateConversion);
		ActivationEmail.emailCheckout(user.getFirstName() + " " + user.getLastName(), user.getEmail(),"Book Id  "+ book.getBookid()+ "  " + "Author  "+ book.getAuthor()+ "  " +"Title  "+book.getTitle()+ "  "+ "Publisher  "+book.getPublisher()+ "  "+ "CheckOut Date  " +time+ "  "+ "Due Date  "+dueDateConversion);
		return model;
		
	}
	}

BookDetails b = new BookDetails();
@RequestMapping(value = "book/createBook", method = RequestMethod.POST )
public String addBookDetails(@RequestParam("bookid")String bookid,
		@RequestParam("author")String author,
		@RequestParam("title") String title,
		@RequestParam("callnum") String callnum,
		@RequestParam("publisher") String publisher,
		@RequestParam("year") String year,
		@RequestParam("location") String location,
		@RequestParam("copies") int copies,
		@RequestParam("status") String status,
		@RequestParam("keywords") String keywords)
	{
		//String id = Long.toString(System.currentTimeMillis());
		b.createBook(bookid, author, title, callnum, publisher, year, location, copies,status, keywords);
			//return "success";
			return "redirect:/book/" + bookid;
	}	

/*
 * //Get the Book Details from Database
 */
@RequestMapping(value = "book/{bookid}", method = RequestMethod.GET )
public ModelAndView updateBook(@PathVariable("bookid")String bookid,HttpServletResponse httpRes){	
		
		ModelAndView model = new ModelAndView("getBookDetailsAndUpdate");
		Book newbook= b.getBookById(bookid);
		
		if(newbook==null){
			httpRes.setStatus( HttpServletResponse.SC_NOT_FOUND);
			model = new ModelAndView("errorPage");
			model.addObject("givenid", bookid);
		}
		else{	
			model = new ModelAndView("getBookDetailsAndUpdate");
			model.addObject("newbook", newbook);
			System.out.println("Obj: "+newbook.getBookid());
		}
		return model;
	}

/*
 * Update the Book details
 */
BookDetails updatebook = new BookDetails();
@RequestMapping(value = "book/{bookid}", method = RequestMethod.POST )
public String updateBookDetails(@RequestParam("bookid")String bookid,
		@RequestParam("author")String author,
		@RequestParam("title") String title,
		@RequestParam("callnum") String callnum,
		@RequestParam("publisher") String publisher,
		@RequestParam("year") String year,
		@RequestParam("location") String location,
		@RequestParam("copies") int copies,
		@RequestParam("status") String status,
		@RequestParam("keywords") String keywords)
	{
		//String id = Long.toString(System.currentTimeMillis());
		updatebook.updateBook(bookid, author, title, callnum, publisher, year, location, copies,status, keywords);
			
			return "redirect:/book/" + bookid;
	}	

/*
 * Delete the Book details
 */
@RequestMapping(value = "book/{bookid}", method = RequestMethod.DELETE )
public String deleteBook(@PathVariable("bookid")  String bookid){
	b.deleteObjectById(bookid);
	return "success";
}


/*
 * Search the Book details based on title
 */
@RequestMapping(value = "book/searchBook", method = RequestMethod.GET )
public ModelAndView searchBook(@RequestParam("userid")String userid){
	ModelAndView model = new ModelAndView("searchBook");
	CreateUser cu = new CreateUser();
	user = new User();
	user = cu.getObjectById(userid);
	model.addObject(user);
	System.out.println("searchbook get");
	return model;
	}



@RequestMapping(value = "book/searchBook", method = RequestMethod.POST )
public ModelAndView searchBooks(@RequestParam("title")String title,
								@RequestParam("userid")String userid)	
{		System.out.println("In cintroller " +title);
		System.out.println("userid"+userid);
		Book newbook =new Book();
		newbook=b.getBookByTitle(title);

		if (newbook !=null)
		{
			ModelAndView model = new ModelAndView("addBookToCart");

			model.addObject("newbook", newbook);
			model.addObject("userid",userid);
			System.out.println("i am booksearch post"+userid);
			System.out.println("In cintroller 22 Nwbook " +newbook.getPublisher());
			return model;
			
		}
		ModelAndView model = new ModelAndView("errormail");
		return model;
}


}





/*
//System.out.println("printing our list of books"+ b.getAllBooks());
//List<String> bookTitles = b.getAllBooks();
//model.addObject("bookDetails", b);
return model;
}

//@RequestMapping(value = "book/getBookTitles", method = RequestMethod.GET)
//public @ResponseBody List<String> searchLastName(@RequestParam("term") String query) {
//
////List<Book> booksList = book.searchClient(new SearchClientEvent(query)).getClients();
////List<String> lastnameList = new ArrayList<>();
////System.out.println("Found clients size: " + clientsList.size());
//List<String> bookList = new ArrayList<>();
//
//BookDetails newbook=new BookDetails();
//bookList= newbook.getAllBookTitles();
//String [] bookArr = new String[bookList.size()];
//bookArr = bookList.toArray();
//System.out.println("List of all books: "+bookList);
//Collections.sort(bookArr);
//return bookArr;
//}
//@RequestMapping(value = "book/getBookTitles", method = RequestMethod.GET,headers="Accept",produces = "application/json")
public @ResponseBody String getBookTitles(@RequestParam("term") String query) throws JsonProcessingException {
List<String> bookTitles = b.getAllBookTitles();

//String [] bookArr = new String[bookTitles.size()];
//bookArr = bookTitles.toArray(bookArr);

//System.out.println("Converted booktitles"+bookArr);
ObjectMapper mapper = new ObjectMapper();
return mapper.writeValueAsString(bookTitles);
//return bookArr;

}
*/