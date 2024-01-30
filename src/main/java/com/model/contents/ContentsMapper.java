package com.model.contents;

import java.util.List;
import java.util.Map;

public interface ContentsMapper {
	int create(ContentsDTO dto);

	List<ContentsDTO> list(Map map);

	int total(Map map);

	ContentsDTO read(int contentsno);

	int update(ContentsDTO dto);

	int updateFile(Map map);
	
	boolean delete(int contentsno);
	
	List<Map> getCategory();
	
	List<ContentsDTO> mainlist(Map map);
	


}
