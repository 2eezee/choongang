package com.oracle.oBootBoard.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oracle.oBootBoard.command.BExcuteCommand;

import jakarta.servlet.http.HttpServletRequest;

// Controller는 무조건 @Controller 해줘야됨 
@Controller
public class BController {
	
	private static final Logger logger = LoggerFactory.getLogger(BController.class);

	private final BExcuteCommand bExcuteService;
	@Autowired
	public BController(BExcuteCommand bExcuteService) {
		this.bExcuteService = bExcuteService;
	}
	
	// call by value vs call by reference 
	// Controller에서는 model 을 써서 데이터를 가져와라 (스프링 규칙이라고 생각 ㄱㄱ)  
	@RequestMapping("/list")
	public String list(Model model) {
		int kkk = 0;
		logger.info("list start...");		
		bExcuteService.bListCmd(model);
		System.out.println("kkk->" + kkk);
		return "list";
	}

	@RequestMapping("/write_view")
	public String write_view(Model model) {
		logger.info("write_view start...");
		return "write_view";
	}
	
	@PostMapping(value = "/write")
	public String write(HttpServletRequest request, Model model) {
		logger.info("write start...");
		
		model.addAttribute("request", request);
		bExcuteService.bWriteCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		bExcuteService.bContentCmd(model);
		
		return "content_view"; 
	}
	
	@RequestMapping(value = "/modify", method =RequestMethod.POST )
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("modify start...");
		
		model.addAttribute("request", request);
		bExcuteService.bModifyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");
		
		model.addAttribute("request", request);
		bExcuteService.bReply_viewCmd(model);
		
		return "reply_view";
		
	}
	
	@RequestMapping(value = "/reply", method =RequestMethod.POST )
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		
		model.addAttribute("request", request);
		bExcuteService.bModifyCmd(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		bExcuteService.bDeleteCmd(model);
		
		return "redirect:list";
		
	}
}
