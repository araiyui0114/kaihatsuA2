package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	//新規登録
		@RequestMapping(value = "/todo/new" )
		public ModelAndView newToDo(ModelAndView mv) {
			mv.setViewName("signup");
			return mv;
		}

		@PostMapping("/todo/new" )
		public ModelAndView newToDo(
				@RequestParam("contents")String contents,
				@RequestParam("date")String date,
				@RequestParam("rank")int rank,
				@RequestParam("color")int color,
				ModelAndView mv) {

			//登録するToDoエンティティのインスタンスを生成
			ToDo todo = new ToDo(contents,date,rank,color);

			//ProductエンティティをProductsテーブルに登録
			//INSERT INTO Products (name.price) VALUES ("ブルーベリー",2000);
			ToDo newT = todoRepository.saveAndFlush(todo);

			//登録したエンティティのコードを取得
			int code = newT.getCode();

			//Thymeleafで表示する準備

			top(mv);

			mv.addObject("CODE",code);

			mv.setViewName("top");

				return mv;
		}


		//更新
		@RequestMapping(value = "/todo/edit" )
		public ModelAndView edit(
//				@RequestParam("code")int code,
//				@RequestParam("contents")String contents,
//				@RequestParam("date")String date,
//				@RequestParam("rank")int rank,
//				@RequestParam("color")int color,
				ModelAndView mv) {

//				mv.addObject("code",code);
//				mv.addObject("contents",contents);
//				mv.addObject("date",date);
//				mv.addObject("rank",rank);
//				mv.addObject("color",color);

			mv.setViewName("edit");
			return mv;
		}

		@PostMapping("/todo/edit" )
		public ModelAndView edit(
				@RequestParam("code")int code,
				@RequestParam("contents")String contents,
				@RequestParam("date")String date,
				@RequestParam("rank")int rank,
				@RequestParam("color")int color,
				ModelAndView mv) {

			//登録するUserエンティティのインスタンスを生成
					ToDo todo = new ToDo(code,contents,date,rank,color);

					//ProductエンティティをProductsテーブルに登録
					todoRepository.saveAndFlush(todo);

					top(mv);


			mv.setViewName("top");
			return mv;
		}

		//削除
		@RequestMapping(value = "/delete")
		public ModelAndView deleteUser(
				@RequestParam("code")int code,
				ModelAndView mv) {

			todoRepository.deleteById(code);

			top(mv);

			mv.setViewName("top");
			return mv;
		}

		//検索

		@RequestMapping(value = "/todo/search" )
		public ModelAndView search(ModelAndView mv) {
			mv.setViewName("search");
			return mv;
		}

		@PostMapping("/todo/search")
		public ModelAndView items(
				ModelAndView mv,
				@RequestParam("contents")String contents,
				@RequestParam("date")String date,
				@RequestParam("rank")int rank,
				@RequestParam("color")int color
				) {

			List<ToDo> todoList = null;

			if (contents.equals("") && date.equals("")) {
				todoList = todoRepository.findAll();
			}

			mv.addObject("todoList", todoList);

//			mv.addObject("contents",contents);
//			mv.addObject("date",date);
//			mv.addObject("rank",rank);
//			mv.addObject("color",color);


			mv.setViewName("searchResult");
			return mv;
		}
		///追加

}
