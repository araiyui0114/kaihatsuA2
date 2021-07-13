package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="todo")
public class ToDo {

			//フィールド

			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name="code")
			private Integer code; //ToDoコード

			@Column(name="contents")
			private String contents; //内容文

			@Column(name="date")
			private String date; //期日(MM/dd/mm)

			@Column(name="usersid")
			private Integer usersid; //ユーザーID

			@Column(name="rank")
			private Integer rank; //優先順位

			@Column(name="color")
			private Integer color; //テキストの色


			//コンストラクタ

			public ToDo() {

			}

			public ToDo(String contents, String date, Integer rank, Integer color) {
				super();
				this.contents = contents;
				this.date = date;
				this.rank = rank;
				this.color = color;
			}

			public ToDo(String contents, String date, Integer usersid, Integer rank, Integer color) {
				super();
				this.contents = contents;
				this.date = date;
				this.usersid = usersid;
				this.rank = rank;
				this.color = color;
			}

			public ToDo(Integer code, String contents, String date, Integer rank, Integer color) {
				super();
				this.code = code;
				this.contents = contents;
				this.date = date;
				this.rank = rank;
				this.color = color;
			}

			public ToDo(Integer code, String contents, String date, Integer usersid, Integer rank, Integer color) {
				super();
				this.code = code;
				this.contents = contents;
				this.date = date;
				this.usersid = usersid;
				this.rank = rank;
				this.color = color;
			}

			//アクセスメソッド

			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getContents() {
				return contents;
			}

			public void setContents(String contents) {
				this.contents = contents;
			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public Integer getId() {
				return usersid;
			}

			public void setId(Integer id) {
				this.usersid = id;
			}

			public Integer getRank() {
				return rank;
			}

			public void setRank(Integer rank) {
				this.rank = rank;
			}

			public Integer getColor() {
				return color;
			}

			public void setColor(Integer color) {
				this.color = color;
			}
			public Integer getUsersid() {
				return usersid;
			}

			public void setUsersid(Integer usersid) {
				this.usersid = usersid;
			}

			//カラーコードへの変換
			public String getColorCode() {
				String colorCode = " ";

				if( color == 1) {
					colorCode = "#ff0000";
				}else if(color == 2) {
					colorCode = "#0000ff";
				}else if(color == 3) {
					colorCode = "#ffff00";
				}
				return colorCode;
			}

			//優先順位への変換
			public String getRankText() {
				String rankText = " ";

				if( rank == 1) {
					rankText = "高";
				}else if( rank == 2) {
					rankText = "中";
				}else if( rank == 3) {
					rankText = "低";
				}
				return rankText;
			}




}
