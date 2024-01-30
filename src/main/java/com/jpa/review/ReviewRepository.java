package com.jpa.review;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends CrudRepository<ReviewDTO, Integer> {

	ReviewDTO findByRnum(int rnum);

	@Query("select count(*) from ReviewDTO r where r.contentsno = :contentsno ")
	int count(@Param("contentsno") int contentsno);

	@Query(value = "select rnum, content, regdate, id, contentsno, star, pname " + "from review r "
			+ "where r.contentsno = ?1 " + "order by rnum desc " + "limit ?2, ?3 ", nativeQuery = true)
	List<ReviewDTO> findList(int contentsno, int sno, int eno);

}
