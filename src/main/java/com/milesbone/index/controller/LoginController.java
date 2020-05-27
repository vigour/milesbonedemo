package com.milesbone.index.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.milesbone.account.entity.User;
import com.milesbone.account.service.IUserService;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private IUserService userservice;
	
	
	@Resource(name=IUserService.SERVICE_NAME)
	public void setUserservice(IUserService userservice) {
		this.userservice = userservice;
	}



	@RequestMapping(value = {"/loginuser"}, method = RequestMethod.GET)
	public ModelAndView loginuser(Model model) {
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from UserController.java **********</div><br><br>";
		model.addAttribute("message", message);
		List<User> users = this.userservice.getAllUser();
		logger.info(users.get(0).toString());
		model.addAttribute("users", users);
		return new ModelAndView("userlogin");
	}
	
	@RequestMapping(value ={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		
		return model;

	}
	
	
	
}
