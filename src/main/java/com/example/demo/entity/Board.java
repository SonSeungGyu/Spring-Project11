package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_Board")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//키 생성방식 (마리아디비에서 auto increament와 같은 기능)
	//키가 String 이면 id 어노테이션만 선언
	int no;
	
	@Column(length=100, nullable=false)
	String title;
	
	@Column(length = 1500, nullable=false)
	String content;
	
	@Column(length = 50, nullable = false)
	String writer;
}
