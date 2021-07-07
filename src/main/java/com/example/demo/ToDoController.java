package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToDoController {

	@Autowired
	HttpSession session;

	@Autowired
	ToDoRepository todoRepository;

	//トップページ
	//http://localhost:8080/
	@RequestMapping("/")
		public ModelAndView top(ModelAndView mv) {

		//DBからリストを取得
		List<ToDo> todoList = todoRepository.findAll();

		//Thymeleafに設定
		mv.addObject("todoList", todoList);

		//top.htmlに設定
		mv.setViewName("top");

		return mv;
	}
}
