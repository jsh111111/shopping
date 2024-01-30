package com.jpa.review;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class ReviewDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int rnum;
	private String content;
	private LocalDate regdate;
	private String id;
	private int contentsno;
	private int star;
	private String pname;
}
