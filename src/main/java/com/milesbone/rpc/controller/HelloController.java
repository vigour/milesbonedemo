package com.milesbone.rpc.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

//	@Autowired
//	private RpcProxy rpcProxy;
//	@RequestMapping("/hello")
//	public void hello(String name){
//		IHelloService service = rpcProxy.create(IHelloService.class);
//		String result = service.hello(name);
//		System.out.println(result);
//	}
//	
//	@RequestMapping("/getUser")
//	public void getUser(String name){
//		IHelloService service = rpcProxy.create(IHelloService.class);
//		System.out.println(service.getUser(name).toString());
//	}
//	
//	@RequestMapping("/getUsers")
//	public void getUsers(int size){
//		IHelloService service = rpcProxy.create(IHelloService.class);
//		List<User> list = service.getUsers(size);
//		for(User user : list){
//			System.out.println(user.toString());
//		}
//	}
//	
//	@RequestMapping("/updateUser")
//	public void updateUser(String name){
//		User user = new User("aaa", name, new Date(), 10000D);
//		IHelloService service = rpcProxy.create(IHelloService.class);
//		user = service.updateUser(user);
//		System.out.println(user.toString());
//	}
}
