package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer>{
	//contentsのあいまい検索
	List<ToDo> findByContentsLikeAndUsersid( String contents,int usersid);

	//dateのあいまい検索
	List<ToDo> findByDateLikeAndUsersid( String date,int usersid);

	//rankの検索
	List<ToDo> findByRankAndUsersid( int rank,int usersid);

	//colorの検索
	List<ToDo> findByUsersidAndColor( int usersid,int color);

	//usersidの検索
	List<ToDo> findByUsersid( int usersid);

	//contents,dateのあいまい検索
	List<ToDo> findByContentsLikeAndDateLikeAndUsersid( String contents, String date,int usersid);
	//contents,date,rankのあいまい検索
	List<ToDo> findByContentsLikeAndDateLikeAndRankAndUsersid( String contents, String date, int rank,int usersid);
	//contents,date,rank,colorのあいまい検索
	List<ToDo> findByContentsLikeAndDateLikeAndRankAndColorAndUsersid( String contents, String date, int rank, int color,int usersid);

	//date,rank,colorのあいまい検索
	List<ToDo> findByDateLikeAndRankAndColorAndUsersid(String date, int rank, int color,int usersid);
	//date,rankのあいまい検索
	List<ToDo> findByDateLikeAndRankAndUsersid(String date, int rank,int usersid);
	//date,colorのあいまい検索
	List<ToDo> findByDateLikeAndColorAndUsersid(String date, int color,int usersid);

	//rank,colorの検索
	List<ToDo> findByRankAndColorAndUsersid(int rank, int color,int usersid);

	//contents,colorの検索
	List<ToDo> findByContentsLikeAndColorAndUsersid( String contents, int color,int usersid);

	//contents,rank,colorのあいまい検索
	List<ToDo> findByContentsLikeAndRankAndColorAndUsersid( String contents, int rank, int color,int usersid);

	//codeの検索
	List<ToDo> findByCode(int code);

	//useridで抽出し、日付昇順
	List<ToDo> findByUsersidOrderByDate(int usersid);
	//useridで抽出し、日付降順
	List<ToDo> findByUsersidOrderByDateDesc(int usersid);

}

