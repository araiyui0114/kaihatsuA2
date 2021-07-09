package com.example.demo;

import java.util.List;

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
	@Autowired
	UsersRepository usersRepository;


	/**
	 * ログイン画面を表示
	 */
	@RequestMapping("/")
	public String login() {
		// セッション情報はクリアする
		session.invalidate();
		return "login";
	}

//	/**
//	 * ログインを実行
//	 */
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public ModelAndView doLogin(
//			@RequestParam("name") String name,
//			ModelAndView mv
//	) {
//		// 名前が空の場合にエラーとする
//		if(name == null || name.length() == 0) {
//			mv.addObject("message", "名前を入力してください");
//			mv.setViewName("index");
//			return mv;
//		}
//
//		// セッションスコープにログイン名とカテゴリ情報を格納する
//		session.setAttribute("name", name);
//		session.setAttribute("ToDo",todoRepository.findAll());
//
//		mv.setViewName("top");
//		return mv;
//	}

	//登録済みの名前でログイン
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("name") String name,
			ModelAndView mv
	) {
		//Usersテーブルから名前を照合
		List<Users> usersList = usersRepository.findByName(name);

		//名前未入力
		if(usersList.size() == 0) {
			mv.addObject("message", "名前を入力してください");
			mv.setViewName("login");
		}
		//照合成功時
		try{
			Users usersInfo = usersList.get(0);
			session.setAttribute("usersInfo", usersInfo);

			// セッションスコープにログイン名とtodo情報を格納する
			session.setAttribute("ToDo", todoRepository.findAll());
			session.setAttribute("name", name);

			//Thymeleafにセット
			//mv.addObject("todoList",usersInfo);
			mv.setViewName("top");

		//例外処理
		}catch(IndexOutOfBoundsException e){
			mv.addObject("message", "名前は登録されていません");
			mv.setViewName("ログイン");
		}

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
