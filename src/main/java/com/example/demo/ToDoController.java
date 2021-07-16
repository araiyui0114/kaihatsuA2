package com.example.demo;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    PointRepository pointRepository;


    //ログイン

    //トップページ！！
    //http://localhost:8080/
    @RequestMapping("/top")
        public ModelAndView top(ModelAndView mv) {

    	Users usersInfo = (Users) session.getAttribute("usersInfo");

    	int usersid = usersInfo.getCode();

    	System.out.println("usersid=" + usersid);

        //DBからリストを取得
        List<ToDo> todoList = todoRepository.findByUsersid(usersid);

        //Thymeleafに設定
        mv.addObject("todoList", todoList);

        //対象のタスクの取得
		List<Point> pointList = null;
		pointList = pointRepository.findByUsersCode(usersid);

		Point point = pointList.get(0);

		Integer usersPoint = point.getPoint();

        mv.addObject("Point",usersPoint);

        //top.htmlに設定
        mv.setViewName("top");

        return mv;
    }

    //カラーリストの表示
    @RequestMapping("/colorList")
        public ModelAndView colorList(
                @RequestParam("colors")int color,
                ModelAndView mv) {
    	Users usersInfo = (Users) session.getAttribute("usersInfo");

    	int usersid = usersInfo.getCode();

        //DBからリストを取得
        List<ToDo> todoList = todoRepository.findByUsersidAndColor(usersid,color);

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

        	//ユーザーIDの取得
             Users usersId = (Users) session.getAttribute("usersInfo");

             int usersid  = usersId.getCode();

            //登録するToDoエンティティのインスタンスを生成
            ToDo todo = new ToDo(contents,date,usersid,rank,color);

            //TodoエンティティをTodoテーブルに登録
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
				@RequestParam("code")int code,
                ModelAndView mv) {

        		//対象のタスクの取得
        		List<ToDo> todoList = null;
        		todoList = todoRepository.findByCode(code);

        		mv.addObject("code",code);
				mv.addObject("todoList",todoList);

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

        	//ユーザーIDの取得
        	Users usersId = (Users) session.getAttribute("usersInfo");

            int usersid  = usersId.getCode();

            //登録するToDoエンティティのインスタンスを生成
                    ToDo todo = new ToDo(code,contents,date,usersid,rank,color);

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

        	Optional<ToDo> record = todoRepository.findById(code);
        	ToDo todo  = record.get();
        	int usersid  = todo.getUsersid();

            todoRepository.deleteById(code);

            //対象のタスクの取得
    		List<Point> pointList = null;
    		pointList = pointRepository.findByUsersCode(usersid);

    		Point point = pointList.get(0);

    		Integer usersPoint = point.getPoint();

    		usersPoint += 10;

    		point.setPoint(usersPoint);

    		//ToDoエンティティをToDoテーブルに登録
            pointRepository.saveAndFlush(point);

            mv.addObject("Point",usersPoint);

            session.removeAttribute("setGoal");
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

        	Users usersInfo = (Users) session.getAttribute("usersInfo");

        	int usersid = usersInfo.getCode();

            List<ToDo> todoList = null;

            //全件検索
            if (contents.equals("") && date.equals("") && rank == 0 && color == 0) {
                todoList = todoRepository.findByUsersid(usersid);
            //contentsあいまい検索
            }else if(!(contents.equals("")) && date.equals("") && rank == 0 && color == 0 && usersid != 0) {
                todoList = todoRepository.findByContentsLikeAndUsersid("%" + contents + "%",usersid);
            //dateあいまい検索
            }else if(contents.equals("") && !(date.equals("")) && rank == 0 && color == 0 && usersid != 0) {
                todoList = todoRepository.findByDateLikeAndUsersid("%" + date + "%",usersid);
            //rank検索
            }else if(contents.equals("") && date.equals("") && rank != 0 && color == 0 && usersid != 0) {
                todoList = todoRepository.findByRankAndUsersid(rank,usersid);
            //color検索
            }else if(contents.equals("") && date.equals("") && rank == 0 && color != 0 && usersid != 0 ){
                todoList = todoRepository.findByUsersidAndColor(color,usersid);
            //contents,dateのあいまい検索
            }else if(!(contents.equals("")) && !(date.equals("")) && rank == 0 && color == 0 && usersid != 0) {
                todoList = todoRepository.findByContentsLikeAndDateLikeAndUsersid("%" + contents + "%","%" + date + "%",usersid);
            //contents,date,rankのあいまい検索
            }else if(!(contents.equals("")) && !(date.equals("")) && rank != 0 && color == 0 && usersid != 0) {
                todoList = todoRepository.findByContentsLikeAndDateLikeAndRankAndUsersid("%" + contents + "%","%" + date + "%",rank,usersid);
            //contents,date,rank,colorのあいまい検索
            }else if(!(contents.equals("")) && !(date.equals("")) && rank != 0 && color != 0 && usersid != 0) {
                todoList = todoRepository.findByContentsLikeAndDateLikeAndRankAndColorAndUsersid("%" + contents + "%","%" + date + "%",rank,color,usersid);
            //date,rank,colorのあいまい検索
            }else if(contents.equals("") && !(date.equals("")) && rank != 0 && color != 0 && usersid != 0) {
                todoList = todoRepository.findByDateLikeAndRankAndColorAndUsersid("%" + date + "%",rank,color,usersid);
            //date,rankのあいまい検索
            }else if(contents.equals("") && !(date.equals("")) && rank != 0 && color == 0 && usersid != 0) {
                todoList = todoRepository.findByDateLikeAndColorAndUsersid("%" + date + "%",rank,usersid);
            //date,colorのあいまい検索
            }else if(contents.equals("") && !(date.equals("")) && rank == 0 && color != 0 && usersid != 0) {
                todoList = todoRepository.findByDateLikeAndColorAndUsersid("%" + date + "%",color,usersid);
            //contents,date,rank,colorのあいまい検索
            }else if(contents.equals("") && date.equals("") && rank != 0 && color != 0 && usersid != 0) {
                todoList = todoRepository.findByRankAndColorAndUsersid(rank,color,usersid);
            //contents,colorのあいまい検索
            }else if(!(contents.equals("")) && date.equals("") && rank == 0 && color != 0 && usersid != 0) {
                todoList = todoRepository.findByContentsLikeAndColorAndUsersid("%" + contents + "%",color,usersid);
            //contents,date,rank,colorのあいまい検索
            }else if(!(contents.equals("")) && date.equals("") && rank != 0 && color != 0 && usersid != 0) {
                todoList = todoRepository.findByContentsLikeAndRankAndColorAndUsersid("%" + contents + "%",rank,color,usersid);
            }

            //検索を結果リストに
            mv.addObject("todoList", todoList);

            mv.setViewName("searchResult");
            return mv;
        }

}
