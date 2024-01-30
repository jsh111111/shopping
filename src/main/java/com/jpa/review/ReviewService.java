package com.jpa.review;

import java.util.List;
import java.util.Map;

public interface ReviewService {

	ReviewDTO create(ReviewDTO ReviewDTO);

	List<ReviewDTO> list(Map map);

	ReviewDTO read(int rnum);

	ReviewDTO update(ReviewDTO ReviewDTO);

	void delete(int rnum);

	int total(int contentsno);

	void bdelete(int contentsno);

	int rcount(int contentsno);

}
