package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer>{
	//contentsのあいまい検索
	List<ToDo> findByContentsLike( String contents);

	//dateのあいまい検索
	List<ToDo> findByDateLike( String date);

	//rankの検索
	List<ToDo> findByRank( int rank);

	//colorの検索
	List<ToDo> findByColor( int color);

	//contents,dateのあいまい検索
	List<ToDo> findByContentsLikeAndDateLike( String contents, String date);
	//contents,date,rankのあいまい検索
	List<ToDo> findByContentsLikeAndDateLikeAndRank( String contents, String date, int rank);
	//contents,date,rank,colorのあいまい検索
	List<ToDo> findByContentsLikeAndDateLikeAndRankAndColor( String contents, String date, int rank, int color);

	//date,rank,colorのあいまい検索
	List<ToDo> findByDateLikeAndRankAndColor(String date, int rank, int color);
	//date,rankのあいまい検索
	List<ToDo> findByDateLikeAndRank(String date, int rank);
	//date,colorのあいまい検索
	List<ToDo> findByDateLikeAndColor(String date, int color);

	//rank,colorの検索
	List<ToDo> findByRankAndColor(int rank, int color);

	//contents,colorの検索
	List<ToDo> findByContentsLikeAndColor( String contents, int color);

	//contents,rank,colorのあいまい検索
	List<ToDo> findByContentsLikeAndRankAndColor( String contents, int rank, int color);

//	//リストの削除
//	List<ToDo> deleteByToDoList();

	//codeの検索
		List<ToDo> findByCode(int code);

}

