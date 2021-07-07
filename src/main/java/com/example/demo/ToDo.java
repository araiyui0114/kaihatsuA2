package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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

			@Column(name="id")
			private Integer id; //ユーザーID

			@Column(name="rank")
			private Integer rank; //優先順位

			@Column(name="color")
			private Integer color; //テキストの色


			//コンストラクタ

			public ToDo() {

			}

			public ToDo(String contents, String date, Integer id, Integer rank, Integer color) {
				super();
				this.contents = contents;
				this.date = date;
				this.id = id;
				this.rank = rank;
				this.color = color;
			}

			public ToDo(Integer code, String contents, String date, Integer id, Integer rank, Integer color) {
				super();
				this.code = code;
				this.contents = contents;
				this.date = date;
				this.id = id;
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
				return id;
			}

			public void setId(Integer id) {
				this.id = id;
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

}
