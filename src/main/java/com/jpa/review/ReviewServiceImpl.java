package com.jpa.review;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.jpa.review.ReviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Override
	public ReviewDTO create(ReviewDTO dto) {
		dto.setRegdate(LocalDate.now());
		return repository.save(dto);

	}

	@Override
	public List<ReviewDTO> list(Map map) {
		int contentsno = (int) map.get("contentsno");
		int sno = (int) map.get("sno");
		int eno = (int) map.get("eno");
		return repository.findList(contentsno, sno, eno);
	}

	@Override
	public ReviewDTO read(int rnum) {
		// TODO Auto-generated method stub
		return repository.findByRnum(rnum);
	}

	@Override
	public ReviewDTO update(ReviewDTO dto) {
		ReviewDTO dto2 = repository.findByRnum(dto.getRnum());
		dto.setId(dto2.getId());
		dto.setRegdate(dto2.getRegdate());
		dto.setContentsno(dto2.getContentsno());
		return repository.save(dto);
	}

	@Override
	public void delete(int rnum) {
		// TODO Auto-generated method stub
		repository.deleteById(rnum);
	}

	@Override
	public int total(int contentsno) {
		// TODO Auto-generated method stub
		return repository.count(contentsno);
	}

	@Override
	public void bdelete(int contentsno) {
		// TODO Auto-generated method stub
		repository.deleteById(contentsno);
	}

	@Override
	public int rcount(int contentsno) {
		// TODO Auto-generated method stub
		return 0;
	}

}
