package edu.sjsu.cmpe275.lab2.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.lab2.dao.CreateLibrarian;
import edu.sjsu.cmpe275.lab2.dao.CreateUser;
import edu.sjsu.cmpe275.lab2.email.ActivationEmail;
import edu.sjsu.cmpe275.lab2.email.TokenGenerator;
import edu.sjsu.cmpe275.lab2.model.Librarian;
import edu.sjsu.cmpe275.lab2.model.User;

@Controller
public class UsersController {

	 User user = new User();
	 Librarian lib = new Librarian();
	CreateUser cu = new CreateUser();
	CreateLibrarian cl= new CreateLibrarian();

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView user() {
		ModelAndView model = new ModelAndView("login");
		return model;
	}

	   @RequestMapping(value = "/librarianHomepage", method = RequestMethod.GET)
		public ModelAndView userHomepage() {
			
		   ModelAndView model = new ModelAndView("librarianHomepage");
			return model;
		}
		
		
		@RequestMapping(value = "/patronHomepage", method = RequestMethod.GET)
		public ModelAndView patronHomepage() {
			ModelAndView model = new ModelAndView("patronHomepage");
			return model;
		}

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("email") String email, @RequestParam("password") String password,HttpServletResponse response) {

		user = new User();
		lib = new Librarian();
		CreateUser cu = new CreateUser();
		CreateLibrarian cl= new CreateLibrarian();
		user = cu.getObjectByEmail(email);
		lib = cl.getLibObjectByEmail(email);

		if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
			Cookie loginCookie = new Cookie("userid",user.getUserid());
				//setting cookie to expiry in 30 mins
				ModelAndView model = new ModelAndView("searchBook");// go to search														// page
				loginCookie.setMaxAge(30*60);
				response.addCookie(loginCookie);
				
				model.addObject(user);
				return model;
			
		} else if (lib != null && lib.getEmail().equals(email) && lib.getPassword().equals(password)) {
			// return "User not registered";
			//return null;
			//ModelAndView model = new ModelAndView("error2"); //no result exception
			
			ModelAndView model = new ModelAndView("librarianHomepage");// go to search														// page
			model.addObject(lib);
			return model;
			
		}
		ModelAndView model = new ModelAndView("errormail");// go to search
		return model;
	}


	@RequestMapping(value = "/userSignup", method = RequestMethod.POST)
	public String signup() {
		return "/signup";

	}

	@RequestMapping(value = "/continue", method = RequestMethod.POST)
	public String signup(@RequestParam("action") String action, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("fName") String fName,
			@RequestParam("lName") String lName, @RequestParam("univid") String univid) {
		 user = new User();
		 lib = new Librarian();
		if (action != null && !action.equals("") && action.equals("Continue")) {
			//&& !user.getEmail().equals(email) 
			String tokenID = TokenGenerator.randomToken();
			
			String type = null;
			if (email.contains("@sjsu.edu")) {
				String univ = null;
				if(univid.length()==6){
					univ=univid;
				}
				else{
					return "/error1"; //validate univid
				}
				type = "librarian";
				lib.setFirstName(fName);
				lib.setLastName(lName);
				lib.setEmail(email);
				lib.setPassword(password);
				lib.setUnivid(univ);
				lib.setUniquecode(tokenID.substring(0, 6));
				ActivationEmail.emailRecommendTrigger(fName + " " + lName, email, lib.getUniquecode());
				return "/continue";
				
			} else {
				type = "patron";
				String univ = null;
				if(univid.length()==6){
					univ=univid;
				}
				else{
					return "/error1"; //validate univid
				}
				user.setFirstName(fName);
				user.setLastName(lName);
				user.setEmail(email);
				user.setPassword(password);
				user.setUnivid(univ);
				user.setUniquecode(tokenID.substring(0, 6));
				ActivationEmail.emailRecommendTrigger(fName + " " + lName, email, user.getUniquecode());
				return "/continue";
			}

		}
		else{
			return "/error1";
		}
		
	}

	@RequestMapping(value = "/Welcome", method = RequestMethod.POST)
	public String continueMethod(@RequestParam("action") String action, @RequestParam("token") String token) {
		CreateUser cu = new CreateUser();
		CreateLibrarian cl= new CreateLibrarian();
		if (action != null && !action.equals("") && action.equals("Submit")) {
			System.out.println("lib  "+lib.getUniquecode());
			System.out.println("usr  "+user.getUniquecode());
			
			if (token != null && token.equals(user.getUniquecode())) {
					cu.insertUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
							user.getUnivid(), user.getUniquecode());
					ActivationEmail.emailAckTrigger(user.getFirstName() + " " + user.getLastName(), user.getEmail(),
							"You Have Succesfully Created an Account");
					return "/login";

				
				}else if(token != null && token.equals(lib.getUniquecode())){
					cl.insertLibrarian(lib.getFirstName(), lib.getLastName(), lib.getEmail(), lib.getPassword(),
							lib.getUnivid(), lib.getUniquecode());
					ActivationEmail.emailAckTrigger(lib.getFirstName() + " " + lib.getLastName(), lib.getEmail(),
							"You Have Succesfully Created an Account");
					return "/login";
				}
	return null;
			} else {
				System.out.println("Not a match");
				return "/error1";
			}
		
	}

}
