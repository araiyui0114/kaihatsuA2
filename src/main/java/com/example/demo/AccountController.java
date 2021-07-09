package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	ToDoRepository todoRepository;

	/**
	 * ログイン画面を表示
	 */
	@RequestMapping("/")
	public String login() {
		// セッション情報はクリアする
		session.invalidate();
		return "login";
	}

	/**
	 * ログインを実行
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("name") String name,
			ModelAndView mv
	) {
		// 名前が空の場合にエラーとする
		if(name == null || name.length() == 0) {
			mv.addObject("message", "名前を入力してください");
			mv.setViewName("index");
			return mv;
		}

		// セッションスコープにログイン名とカテゴリ情報を格納する
		session.setAttribute("name", name);
		session.setAttribute("ToDd",todoRepository.findAll());

		mv.setViewName("top");
		return mv;
	}

	/**
	 * ログアウトを実行
	 */
	@RequestMapping("/logout")
	public String logout() {
		// ログイン画面表示処理を実行するだけ
		return login();
	}
}
