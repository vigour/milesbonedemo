package com.milesbone.sysuer.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.milesbone.sysuer.entity.Sysuser;
import com.milesbone.sysuer.service.ISysuserService;

@Controller
public class SysuserController {

	private ISysuserService sysuserService;

	@Resource(name=ISysuserService.SERVICE_NAME)
	public void setSysuserService(ISysuserService sysuserService) {
		this.sysuserService = sysuserService;
	}
	
	@RequestMapping(value="/sysuserlogin",method=RequestMethod.GET)
	public ModelAndView addSysuser(Model model) {
		String message = "<br><div style='text-align:center;'>"
				+ "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from UserController.java **********</div><br><br>";
		model.addAttribute("message", message);
		List<Sysuser> users = this.sysuserService.getAllUser();
		model.addAttribute("users", users);
		return new ModelAndView("userlogin");
	}
	
}
