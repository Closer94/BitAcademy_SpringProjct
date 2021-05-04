package com.saltlux.mydictionary.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.saltlux.mydictionary.security.Auth;
import com.saltlux.mydictionary.service.OneToOneService;
import com.saltlux.mydictionary.vo.OneToOneVo;
import com.saltlux.mydictionary.vo.UserVo;

@Controller
@RequestMapping("/oneToOne")
public class OneToOneController {

	@Autowired
	private OneToOneService oneToOneService;

	@Auth
	@RequestMapping("")
	public String index(HttpSession session, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		int totalCnt = oneToOneService.findAllCnt(authUser.getId());
		List<OneToOneVo> list = null;		
		int page = 1;		
		
		if(authUser.getId().equals("admin")) { //관리자일때는 닉네임별 글을 불러올 필요없이 전체 글을 가져와야한다.
			list = oneToOneService.findAll(page); 
		} else{ 
			list = oneToOneService.findAll(page, authUser.getId());
		}
		model.addAttribute("list", list);
		model.addAttribute("p", page);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("authUser", authUser);
		
		return "oneToOne/index";
	}

	@RequestMapping(value = "/{page}")
	public String index(HttpSession session, @PathVariable("page") int page, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		int totalCnt = oneToOneService.findAllCnt(authUser.getId());
		List<OneToOneVo> list = null;		
		
		if(authUser.getId().equals("admin")) { //관리자일때는 닉네임별 글을 불러올 필요없이 전체 글을 가져와야한다.
			list = oneToOneService.findAll(page); 
		} else{ 
			list = oneToOneService.findAll(page, authUser.getId());
		}
		model.addAttribute("list", list);
		model.addAttribute("p", page);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("authUser", authUser);
		
		return "oneToOne/index";
	}

	@RequestMapping("/writeForm")
	public String writeForm() {

		return "oneToOne/write";
	}

	@RequestMapping("/write")
	public String write(HttpSession session, @RequestParam String title, @RequestParam String content) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		OneToOneVo vo = new OneToOneVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriter(authUser.getId());
		oneToOneService.write(vo);
		
		return "redirect:/oneToOne/";
	}

	@RequestMapping(value = "/detail/{no}")
	public String detail(@PathVariable("no") String no, Model model) {
		OneToOneVo vo = oneToOneService.findOne(no);

		model.addAttribute("vo", vo);
		model.addAttribute("no", no);
		return "oneToOne/detail";
	}

	@RequestMapping("/modifyForm/{no}")
	public String modifyForm(@PathVariable("no") String no, Model model) {
		OneToOneVo vo = oneToOneService.findOne(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("no", no);
		
		return "oneToOne/modify";
	}
	
	@RequestMapping("/modify/{no}")
	public String modify(@PathVariable("no") String no, @RequestParam String title, @RequestParam String content) {
		
		oneToOneService.update(no, title, content);
		
		return "redirect:/oneToOne/";
	}

	@RequestMapping("/search")
	public String search(@RequestParam(defaultValue = "title") String searchOption,
			@RequestParam(defaultValue = "") String keyword, Model model) {
		int page = 1;
		List<OneToOneVo> list = oneToOneService.search(searchOption, keyword, page);
		model.addAttribute("list", list);
		model.addAttribute("p", page);

		return "oneToOne/search";
	}

	@RequestMapping(value = "/search/{page}")
	public String search(@RequestParam(defaultValue = "title") String searchOption,
			@RequestParam(defaultValue = "") String keyword, @PathVariable("page") int page, Model model) {
		List<OneToOneVo> list = oneToOneService.search(searchOption, keyword, page);
		model.addAttribute("list", list);
		model.addAttribute("p", page);

		return "oneToOne/search";
	}
	
	@RequestMapping(value = "/reply/{no}")
	public String detail(@PathVariable("no") String no, @RequestParam String reply, Model model) {
		OneToOneVo vo = oneToOneService.findOne(no);
		vo.setReply(reply);
		oneToOneService.insertReply(no, reply);
		model.addAttribute("vo", vo);
		return "redirect:/oneToOne/detail/" + no;
	}
}