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

	//カラーリストの表示
	@RequestMapping("/colorList")
		public ModelAndView colorList(
				@RequestParam("red")int color,
				ModelAndView mv) {

		//DBからリストを取得
		List<ToDo> todoList = todoRepository.findByColor(color);

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
				//複数項目の取り出し
				@RequestParam(name= "goal", required=false)String[] goal,
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

			//目標の設定
			String setGoal = "";

			if(goal == null) {
				setGoal = "";
			}else {
				setGoal = contents;

				session.setAttribute("setGoal", setGoal);
			}
			session.getAttribute(setGoal);

			mv.addObject("setGoal",setGoal);

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
				//複数項目の取り出し
				@RequestParam(name= "goal", required=false)String[] goal,
				ModelAndView mv) {

			//登録するToDoエンティティのインスタンスを生成
					ToDo todo = new ToDo(code,contents,date,rank,color);

//					String colorcode = null;

					//ToDoエンティティをToDoテーブルに登録
					todoRepository.saveAndFlush(todo);

					top(mv);

					//目標の設定
					String setGoal = "";

					if(goal == null) {
						setGoal = "";
					}else {
						setGoal = contents;
						//目標をセッションに追加
						session.setAttribute("setGoal", setGoal);
					}

					session.getAttribute(setGoal);

					mv.addObject("setGoal",setGoal);

					mv.setViewName("top");

					return mv;
				}

		//削除
		@RequestMapping(value = "/delete")
		public ModelAndView delete(
				@RequestParam("code")int code,
				ModelAndView mv) {

			todoRepository.deleteById(code);

			top(mv);

			mv.setViewName("top");
			return mv;
		}

//		//全件削除
//		@RequestMapping(value = "/allDelete")
//		public ModelAndView delete(ModelAndView mv) {
//
//			todoRepository.delete(ToDo entity);
//
//			top(mv);
//
//			mv.setViewName("top");
//			return mv;
//		}

//		//リスト削除
//				@RequestMapping(value = "/listDelete")
//				public ModelAndView listDelete(
//						@RequestParam("color")int color,
//						ModelAndView mv) {
//
//					//取得したカラーのリストを抽出
//					List<ToDo> todoList = null;
//					todoList = todoRepository.findByColor(color);
//
//					//
//					todoRepository.deleteByToDoList(todoList);
//
//					top(mv);
//
//					mv.setViewName("top");
//					return mv;
//				}



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

			//全件検索
			if (contents.equals("") && date.equals("") && rank == 0 && color == 0) {
				todoList = todoRepository.findAll();
			//contentsあいまい検索
			}else if(!(contents.equals("")) && date.equals("") && rank == 0 && color == 0) {
				todoList = todoRepository.findByContentsLike("%" + contents + "%");
			//dateあいまい検索
			}else if(contents.equals("") && !(date.equals("")) && rank == 0 && color == 0) {
				todoList = todoRepository.findByDateLike("%" + date + "%");
			//rank検索
			}else if(contents.equals("") && date.equals("") && rank != 0 && color == 0) {
				todoList = todoRepository.findByRank(rank);
			//color検索
			}else if(contents.equals("") && date.equals("") && rank == 0 && color != 0) {
				todoList = todoRepository.findByColor(color);
			//contents,dateのあいまい検索
			}else if(!(contents.equals("")) && !(date.equals("")) && rank == 0 && color == 0) {
				todoList = todoRepository.findByContentsLikeAndDateLike("%" + contents + "%","%" + date + "%");
			//contents,date,rankのあいまい検索
			}else if(!(contents.equals("")) && !(date.equals("")) && rank != 0 && color == 0) {
				todoList = todoRepository.findByContentsLikeAndDateLikeAndRank("%" + contents + "%","%" + date + "%",rank);
			//contents,date,rank,colorのあいまい検索
			}else if(!(contents.equals("")) && !(date.equals("")) && rank != 0 && color != 0) {
				todoList = todoRepository.findByContentsLikeAndDateLikeAndRankAndColor("%" + contents + "%","%" + date + "%",rank,color);
			//date,rank,colorのあいまい検索
			}else if(contents.equals("") && !(date.equals("")) && rank != 0 && color != 0) {
				todoList = todoRepository.findByDateLikeAndRankAndColor("%" + date + "%",rank,color);
			//date,rankのあいまい検索
			}else if(contents.equals("") && !(date.equals("")) && rank != 0 && color == 0) {
				todoList = todoRepository.findByDateLikeAndColor("%" + date + "%",rank);
			//date,colorのあいまい検索
			}else if(contents.equals("") && !(date.equals("")) && rank == 0 && color != 0) {
				todoList = todoRepository.findByDateLikeAndColor("%" + date + "%",color);
			//contents,date,rank,colorのあいまい検索
			}else if(contents.equals("") && date.equals("") && rank != 0 && color != 0) {
				todoList = todoRepository.findByRankAndColor(rank,color);
			//contents,colorのあいまい検索
			}else if(!(contents.equals("")) && date.equals("") && rank == 0 && color != 0) {
				todoList = todoRepository.findByContentsLikeAndColor("%" + contents + "%",color);
			//contents,date,rank,colorのあいまい検索
			}else if(!(contents.equals("")) && date.equals("") && rank != 0 && color != 0) {
				todoList = todoRepository.findByContentsLikeAndRankAndColor("%" + contents + "%",rank,color);
			}

			//検索を結果リストに
			mv.addObject("todoList", todoList);

			mv.setViewName("searchResult");
			return mv;
		}
}
