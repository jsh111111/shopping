package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.UploadCon;
import com.model.contents.ContentsDTO;
import com.model.contents.ContentsService;
import com.utility.Utility;

@Controller
public class ContentsController {

	@Autowired
	@Qualifier("com.model.contents.ContentsServiceImpl")
	private ContentsService service;

	// 상품 등록
	@GetMapping("/admin/contents/create")
	public String create() {

		return "/contents/create";
	}

	@PostMapping("/contents/create")
	public String create(ContentsDTO dto, HttpServletRequest request) throws IOException {
		// 이미지 파일 업로드
		// 1. storage 경로에 순수파일로 rename 해서 저장
		String filename = Utility.saveFileSpring(dto.getFilenameMF(), UploadCon.getUploadDir());
		long size = (int) dto.getFilenameMF().getSize();

		if (size > 0) {
			dto.setFilename(filename);
		} else {
			dto.setFilename("default.jpg");
		}

		// 등록
		int cnt = service.create(dto);
		if (cnt > 0) {
			return "redirect:/";
		} else {
			return "error";
		}
	}

	// 상품 목록
	@RequestMapping("/contents/list")
	public String list(HttpServletRequest request) {
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));

		if (col.equals("total")) {
			word = "";
		}

		int nowPage = 1;
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}

		int eno = 5;
		int sno = (nowPage - 1) * eno;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);

		List<ContentsDTO> list = service.list(map);
		int total = service.total(map);
		String url = "/contents/list";
		String paging = Utility.paging(total, nowPage, eno, col, word, url);

		request.setAttribute("list", list);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("paging", paging);

		return "/contents/list";
	}

	// 상품 조회
	@GetMapping("/contents/read")
	public String read(int contentsno, Model model) {
		model.addAttribute("dto", service.read(contentsno));

		return "/contents/read";
	}

	// 상품 수정
	@GetMapping("/contents/update/{contentsno}")
	public String update(@PathVariable int contentsno, Model model) {
		model.addAttribute("dto", service.read(contentsno));

		return "/contents/update";
	}

	@PostMapping("/contents/update")
	public String update(ContentsDTO dto) {
		int cnt = service.update(dto);

		if (cnt > 0) {
			return "redirect:/contents/list";
		} else {
			return "error";
		}
	}

	// 상품 그림 수정
	@GetMapping("/contents/updateFile/{contentsno}/{oldfile}")
	public String updateFile(@PathVariable int contentsno, @PathVariable String oldfile, Model model) {
		model.addAttribute("contentsno", contentsno);
		model.addAttribute("oldfile", oldfile);

		return "/contents/updateFile";
	}

	@PostMapping("/contents/updateFile")
	public String updateFile(int contentsno, String oldfile, MultipartFile filenameMF, HttpServletRequest request) {
		if (oldfile != null && !oldfile.equals("default.jpg")) { // 원본파일 삭제
			Utility.deleteFile(UploadCon.getUploadDir(), oldfile);
		}

		// 변경 파일 저장
		String filename = Utility.saveFileSpring(filenameMF, UploadCon.getUploadDir());
		Map map = new HashMap();
		map.put("contentsno", contentsno);
		map.put("filename", filename);

		int cnt = service.updateFile(map);
		if (cnt > 0) {
			return "redirect:/contents/list";
		} else {
			return "error";
		}
	}

	// 상품 삭제
	@GetMapping("/contents/delete/{contentsno}/{filename}")
	public String delete(@PathVariable int contentsno, @PathVariable String filename) {
		boolean flag = false;

		try {
			if (filename != null && !filename.equals("default.jpg")) { // 파일 삭제
				Utility.deleteFile(UploadCon.getUploadDir(), filename);
			}
			flag = service.delete(contentsno);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (flag) {
			return "redirect:/contents/list";
		} else {
			return "error";
		}
	}

	// 최상위 메뉴 목록
	@GetMapping(value = "/contents/getCategory", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Map> getCategory(HttpServletRequest request) {
		List<Map> list = service.getCategory();

		return list;
	}

	// 고객용 상품목록
	@GetMapping("/contents/mainlist/{cateno}")
	public String mainlist(@PathVariable("cateno") int cateno, HttpServletRequest request, Model model) {
		// 검색관련------------------------
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));

		if (col.equals("total")) {
			word = "";
		}

		// 페이지관련-----------------------
		int nowPage = 1;// 현재 보고있는 페이지
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		int recordPerPage = 8;// 한페이지당 보여줄 레코드갯수

		// (MySQL) DB에서 가져올 순번-----------------
		int sno = (nowPage - 1) * recordPerPage;
		int eno = recordPerPage;

		Map map = new HashMap();
		map.put("col", "cateno");
		map.put("word", cateno);

		int total = service.total(map);

		map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("cateno", cateno);

		List<ContentsDTO> list = service.mainlist(map);

		String url = "/contents/mainlist/?cateno=" + cateno;

		String paging = Utility.paging2(total, nowPage, recordPerPage, col, word, url);

		// request에 Model사용 결과 담는다
		request.setAttribute("list", list);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("paging", paging);
		request.setAttribute("cateno", cateno);

		return "/contents/mainlist";
	}

	// 상품 상세 페이지
	@GetMapping("/contents/detail/{contentsno}")
	public String detail(@PathVariable("contentsno") int contentsno, Model model) {
		model.addAttribute("dto", service.read(contentsno));

		return "/contents/detail";
	}
}