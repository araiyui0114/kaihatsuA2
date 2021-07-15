package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
	@Autowired
	PointRepository pointRepository;


	/**
	 * ログイン画面を表示
	 */
	@RequestMapping("/")
	public String login() {
		// セッション情報はクリアする
		session.invalidate();
		return "login";
	}

	//登録済みの名前でログイン
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView doLogin(
			@RequestParam("name") String name,
			ModelAndView mv
	) {
		//Usersテーブルから名前を照合
		List<Users> usersList = usersRepository.findByName(name);

		//名前未入力
		if(name == null || name.length() == 0) {
			mv.addObject("message", "名前を入力してください");
			mv.setViewName("login");
			return mv;
		}
		//照合成功時
		try{
			Users usersInfo = usersList.get(0);
			session.setAttribute("usersInfo", usersInfo);

			int usersid = usersInfo.getCode();

			// セッションスコープにログイン名とtodo情報を格納する
			mv.addObject("todoList", todoRepository.findByUsersid(usersid));
			session.setAttribute("name", name);

			//対象のタスクの取得
			List<Point> pointList = null;
			pointList = pointRepository.findByUsersCode(usersid);

			Point point = pointList.get(0);

			Integer usersPoint = point.getPoint();

	        mv.addObject("Point",usersPoint);

			//Thymeleafにセット
			//mv.addObject("todoList",usersInfo);
			mv.setViewName("top");

		//例外処理
		}catch(IndexOutOfBoundsException e){
			mv.addObject("message", "名前は登録されていません");
			mv.setViewName("login");
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

	//新規登録
		@RequestMapping(value = "/users/new" )
		public ModelAndView newUser(ModelAndView mv) {
			mv.setViewName("addUser");
			return mv;
		}

		@PostMapping("/users/new" )
		public ModelAndView newUser(
				@RequestParam("name")String name,
				@RequestParam("email")String email,
				@RequestParam("password")String password,
				ModelAndView mv) {

			//名前未入力
			if(name == null || name.length() == 0 ||
			email == null || email.length() == 0||
			password == null || password.length() == 0) {
				mv.addObject("message", "情報を入力してください");
				mv.setViewName("addUser");
				return mv;
			}

			//登録するUserエンティティのインスタンスを生成
			Users users = new Users(name,email,password);

			//ProductエンティティをProductsテーブルに登録
			//INSERT INTO Products (name.price) VALUES ("ブルーベリー",2000);
			Users newU = usersRepository.saveAndFlush(users);

			//登録したエンティティのコードを取得
			int code = newU.getCode();

			Integer userscode = code;
			Integer point = 0;

			//登録するUserエンティティのインスタンスを生成
			Point usersPoint = new Point(userscode,point);

			//ProductエンティティをProductsテーブルに登録
			//INSERT INTO Products (name.price) VALUES ("ブルーベリー",2000);
			Point newP = pointRepository.saveAndFlush(usersPoint);



			//Thymeleafで表示する準備

			mv.addObject("CODE",code);

			mv.setViewName("login");

				return mv;
		}



		//更新
		@RequestMapping(value = "/users/edit" )
		public ModelAndView edituser(ModelAndView mv) {
			mv.setViewName("usersEdit");
			return mv;
		}

		@RequestMapping(value = "/users/edit/now" )
		public ModelAndView edituser(
				@RequestParam("name")String name,
				ModelAndView mv) {
			//名前未入力
			if(name == null || name.length() == 0) {
				mv.addObject("message", "名前を入力してください");
				mv.setViewName("usersEdit");
				return mv;
			}
			try {
			List<Users> usersList = usersRepository.findByName(name);

			Users users = usersList.get(0);

			Integer usersCode = users.getCode();
			String usersName = users.getName();
			String usersEmail = users.getEmail();
			String usersPassword = users.getPassword();


			mv.addObject("code",usersCode);
			mv.addObject("name",usersName);
			mv.addObject("email",usersEmail);
			mv.addObject("password",usersPassword);

			mv.setViewName("editUser");
			return mv;
		//例外処理
		}catch(IndexOutOfBoundsException e){
			mv.addObject("message", "名前は登録されていません");
			mv.setViewName("usersEdit");
			return mv;
		}
		}

		@PostMapping("/edit" )
		public ModelAndView editUser(
				@RequestParam("code")int code,
				@RequestParam("name")String name,
				@RequestParam("email")String email,
				@RequestParam("password")String password,
				ModelAndView mv) {

			//登録するUserエンティティのインスタンスを生成
					Users user = new Users(code,name,email,password);

					//ProductエンティティをProductsテーブルに登録
					//INSERT INTO Products (name.price) VALUES ("ブルーベリー",2000);
					Users newU = usersRepository.saveAndFlush(user);

			mv.setViewName("login");
			return mv;
		}

		//削除
		@RequestMapping(value = "/users/delete" )
		public ModelAndView deleteUser(ModelAndView mv) {
			mv.setViewName("usersDelete");
			return mv;
		}

		@RequestMapping(value = "/users/delete/now" )
		public ModelAndView deleteUser(
				@RequestParam("name")String name,
				ModelAndView mv) {

			//名前未入力
			if(name == null || name.length() == 0) {
				mv.addObject("message", "名前を入力してください");
				mv.setViewName("usersDelete");
				return mv;
			}
			try {
			List<Users> usersList = usersRepository.findByName(name);

			Users users = usersList.get(0);

			Integer usersCode = users.getCode();

			usersRepository.deleteById(usersCode);

			mv.setViewName("login");
			return mv;
			//例外処理
			}catch(IndexOutOfBoundsException e){
				mv.addObject("message", "名前は登録されていません");
				mv.setViewName("usersDelete");
				return mv;
			}

		}

}
