package com.milesbone.controller.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import com.milesbone.base.AbstractControllerTestCase;

public class UserControllerTest extends AbstractControllerTestCase {
	
	
	@Test
	public void userlogin() throws Exception {

		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk()).andExpect(view().name("userlogin"));
	}
}
