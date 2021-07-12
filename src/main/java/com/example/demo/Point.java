package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Point")
public class Point {

	//フィールド
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="code")
	private Integer code;

	@Column(name="usersCode")
	private Integer usersCode;

	@Column(name="point")
	private Integer point;

	//コンストラクタ
	public Point() {
		super();
	}

	public Point(Integer code, Integer usersCode, Integer point) {
			super();
			this.code = code;
			this.usersCode = usersCode;
			this.point = point;
		}

	//アクセスメソッド
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getUserscode() {
		return usersCode;
	}

	public void setUserscode(Integer userscode) {
		this.usersCode = userscode;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
}
