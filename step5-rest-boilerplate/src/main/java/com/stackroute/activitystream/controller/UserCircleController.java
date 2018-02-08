package com.stackroute.activitystream.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.stackroute.activitystream.model.UserCircle;
import com.stackroute.activitystream.service.CircleService;
import com.stackroute.activitystream.service.UserCircleService;
import com.stackroute.activitystream.service.UserService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

public class UserCircleController {

	/*
	 * Autowiring should be implemented for the
	 * UserService,UserCircleService,CircleService,UserCircle. Please note that we
	 * should not create any object using the new keyword
	 */

	@Autowired
	private UserCircleService userCircleService;

	@Autowired
	private UserService userService;

	@Autowired
	private CircleService circleService;
	
	@Autowired
	private UserCircle userCircle;

	/*
	 * Define a handler method which will add a user to a circle.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user is added to the circle 2.
	 * 500(INTERNAL SERVER ERROR) - If there are any errors 3. 401(UNAUTHORIZED) -
	 * If the user is not logged in 4. 404(NOT FOUND) - if the username or the
	 * circle does not exist 5. 409(CONFLICT) - if the user is already added to the
	 * circle
	 * 
	 * This handler method should map to the URL
	 * "/api/usercircle/addToCircle/{username}/{circleName}" using HTTP PUT method"
	 * where "username" should be replaced by a valid username without {} and
	 * "circleName" should be replaced by a valid circle name without {}
	 */

	@PutMapping("/api/usercircle/addToCircle/{username}/{circleName}")
	public ResponseEntity<UserCircle> addUserCircle(@PathVariable("username") String username,
			@PathVariable("circleName") String circleName, HttpSession session) {
		if (session.getAttribute("username") != null) {
			if (userService.get(username) != null && circleService.get(circleName) != null) {
				if (userCircleService.get(username, circleName) == null) {
					if (userCircleService.addUser(username, circleName)) {
						/*userCircle.setUsername(username);
						userCircle.setCircleName(circleName);*/
						return new ResponseEntity<UserCircle>(
								HttpStatus.OK);
					} else {
						return new ResponseEntity<UserCircle>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					return new ResponseEntity<UserCircle>(HttpStatus.CONFLICT);
				}
			} else {
				return new ResponseEntity<UserCircle>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<UserCircle>(HttpStatus.UNAUTHORIZED);
		}
	}

	/*
	 * Define a handler method which will remove a user from a circle.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user is remove from the circle 2.
	 * 500(INTERNAL SERVER ERROR) - If there are any errors 3. 401(UNAUTHORIZED) -
	 * If the user is not logged in
	 * 
	 * This handler method should map to the URL
	 * "/api/usercircle/removeFromCircle/{username}/{circleName}" using HTTP PUT
	 * method" where "username" should be replaced by a valid username without {}
	 * and "circleName" should be replaced by a valid circle name without {}
	 */

	 @PutMapping("/api/usercircle/removeFromCircle/{username}/{circleName}")
	   public ResponseEntity<UserCircle> removeCircle(@PathVariable("username")String username,@PathVariable("circleName")String circleName,HttpSession session)
	   {
		   UserCircle userCircle;
		   
		   if(session.getAttribute("username")!=null)
		   {
			   /*if((userCircle=userCircleService.get(username, circleName))!=null)
			   {*/
				   if(userCircleService.removeUser(username, circleName))
				   {
					     return new ResponseEntity<UserCircle>(HttpStatus.OK);
				   }else
				   {
					   return new ResponseEntity<UserCircle>(HttpStatus.INTERNAL_SERVER_ERROR);
				   }
			  /* }else
			   {
				   return new ResponseEntity<UserCircle>(HttpStatus.CONFLICT);
			   }*/
		   }else
		   {
			   return new ResponseEntity<UserCircle>(HttpStatus.UNAUTHORIZED);
		   }
	   }
	
	
	
	
	/*
	 * Define a handler method which will get us the subscribed circles by a user.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user is added to the circle 2.
	 * 401(UNAUTHORIZED) - If the user is not logged in
	 * 
	 * This handler method should map to the URL
	 * "/api/usercircle/searchByUser/{username}" using HTTP GET method where
	 * "username" should be replaced by a valid username without {}
	 */

	   @GetMapping("/api/usercircle/searchByUser/{username}")
	   public ResponseEntity<List<String>> getUserCircle(@PathVariable("username")String username,HttpSession session)
	   {
		   if(session.getAttribute("username")!=null)
		   {
			   return new ResponseEntity<List<String>>(userCircleService.getMyCircles(username),HttpStatus.OK);
		   }else
		   {
			   return new ResponseEntity<List<String>>(HttpStatus.UNAUTHORIZED);
		   }
	   }
}
