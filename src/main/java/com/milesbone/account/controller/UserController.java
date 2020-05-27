package com.milesbone.account.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.milesbone.account.entity.User;
import com.milesbone.account.service.IUserService;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private IUserService userservice;

	
//	private RpcProxy rpcProxy;
	
	@Resource(name = IUserService.SERVICE_NAME)
	public void setUserservice(IUserService userservice) {
		this.userservice = userservice;
	}


//	@Autowired
//	public void setRpcProxy(RpcProxy rpcProxy) {
//		this.rpcProxy = rpcProxy;
//	}





	@RequestMapping(value="/userlogin",method=RequestMethod.POST)
	public ModelAndView userlogin(Model model) {
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from UserController.java **********</div><br><br>";
//		IHelloService helloService = rpcProxy.create(IHelloService.class);
//		helloService.hello("test");
		model.addAttribute("message", message);
		List<User> users = this.userservice.getAllUser();
		logger.debug(users.get(0).toString());
		model.addAttribute("users", users);
		return new ModelAndView("userlogin");
	}
}
