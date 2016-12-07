package edu.sjsu.cmpe275.lab2.controller;


import java.io.IOException;
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
import edu.sjsu.cmpe275.lab2.model.Book;
//import edu.sjsu.cmpe275.lab2.dao.CreateUser;
import edu.sjsu.cmpe275.lab2.model.User;


@Controller
public class BookCrudController {

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
	BookCheckout bC = new BookCheckout();
	//String condition = bC.addBooksToPatron(userid, bookid);
	/*if (condition == "bookLimitReached")
	{
		ModelAndView model = new ModelAndView("BLR");
		String value = "Book Limit Reached";
		model.addObject("limitReached", value);
		return model;
	}
	else if(condition == "bookLimitReachedForTheDay")
	{
		ModelAndView model = new ModelAndView("BLR");
		String value = "Book Limit Reached For The Day";
		model.addObject("limitReached", value);
		return model;
	}
	else
	{*/
		ModelAndView model = new ModelAndView("searchBook");
		return model;
	/*}*/
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
public ModelAndView searchBook(){
	ModelAndView model = new ModelAndView("searchBook");
	return model;
	}



@RequestMapping(value = "book/searchBook", method = RequestMethod.POST )
public ModelAndView searchBooks(@RequestParam("title")String title)	
{		System.out.println("In cintroller " +title);
		Book newbook =new Book();
		newbook=b.getBookByTitle(title);
		
		System.out.println("In controller Nwbook " +newbook.getTitle());
		

		ModelAndView model = new ModelAndView("addBookToCart");

		model.addObject("newbook", newbook);
		System.out.println("In cintroller 22 Nwbook " +newbook.getPublisher());
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