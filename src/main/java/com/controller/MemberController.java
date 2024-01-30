package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.UploadMem;
import com.model.member.MemberDTO;
import com.model.member.MemberService;
import com.utility.Utility;

@Controller
public class MemberController {

	@Autowired
	@Qualifier("com.model.member.MemberServiceImpl")
	private MemberService service;

	@GetMapping("/")
	public String home() {

		return "/home";
	}

	/** 아이디 중복 확인 **/
	@GetMapping(value = "/member/idcheck", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> idcheck(String id) {
		int cnt = service.duplicatedId(id);

		Map<String, String> map = new HashMap<String, String>();
		if (cnt > 0) {
			map.put("str", id + "는 중복되어서 사용할 수 없습니다.");
		} else {
			map.put("str", id + "는 중복 아님, 사용 가능합니다.");
		}
		return map;
	}

	/** 이메일 중복 확인 **/
	@GetMapping(value = "/member/emailcheck", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> emailcheck(String email) {
		int cnt = service.duplicatedEmail(email);

		Map<String, String> map = new HashMap<String, String>();
		if (cnt > 0) {
			map.put("str", email + "는 중복되어서 사용할 수 없습니다.");
		} else {
			map.put("str", email + "는 중복 아님, 사용 가능합니다.");
		}
		return map;
	}

	/** 동의 페이지 **/
	@GetMapping("/member/agree")
	public String agree() {

		return "/member/agree";
	}

	/** 회의가입 페이지 **/
	@PostMapping("/member/createForm")
	public String create() {

		return "/member/create";
	}

	/** 회원가입 이미지 저장, 아이디, 이메일 중복 버튼 체크 **/
	@PostMapping("/member/create")
	public String create(MemberDTO dto, HttpServletRequest request) {

		int cnta = service.duplicatedId(dto.getId());
		int cntb = service.duplicatedEmail(dto.getEmail());

		if (cnta > 0 || cntb > 0) {
			return "createError";
		}

		String fname = Utility.saveFileSpring(dto.getFnameMF(), UploadMem.getUploadDir());
		long fsize = dto.getFnameMF().getSize();

		if (fsize == 0) {
			fname = "member.jpg";
		}

		dto.setFname(fname);
		int cnt = service.create(dto);

		if (cnt > 0) {
			return "redirect:/";
		} else {
			return "error";
		}

	}

	/* 로그인 */
	@GetMapping("/member/login")
	public String login(HttpServletRequest request) {
		/*----쿠키설정 내용시작----------------------------*/
		String c_id = ""; // ID 저장 여부를 저장하는 변수, Y
		String c_id_val = ""; // ID 값

		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];

				if (cookie.getName().equals("c_id")) {
					c_id = cookie.getValue(); // Y
				} else if (cookie.getName().equals("c_id_val")) {
					c_id_val = cookie.getValue(); // user1...
				}
			}
		}
		/*----쿠키설정 내용 끝----------------------------*/

		request.setAttribute("c_id", c_id);
		request.setAttribute("c_id_val", c_id_val);

		return "/member/login";
	}

	@PostMapping("/member/login")
	public String login(@RequestParam Map<String, String> map, HttpSession session, HttpServletResponse response,
			HttpServletRequest request, Model model) {
		int cnt = service.loginCheck(map);

		if (cnt > 0) { // 회원일 경우
			session.setAttribute("id", map.get("id"));

			Map<String, String> gmap = service.getGrade(map.get("id"));
			session.setAttribute("id", map.get("id"));
			session.setAttribute("grade", gmap.get("grade"));
			session.setAttribute("mname", gmap.get("mname"));

			// Cookie 저장,id저장 여부 및 id
			Cookie cookie = null;
			String c_id = request.getParameter("c_id");
			if (c_id != null) {
				cookie = new Cookie("c_id", c_id); // c_id=> Y
				cookie.setMaxAge(60 * 60 * 24 * 365);// 1년
				response.addCookie(cookie);// 요청지(client:브라우저 컴) 쿠키 저장

				cookie = new Cookie("c_id_val", map.get("id"));
				cookie.setMaxAge(60 * 60 * 24 * 365);// 1년
				response.addCookie(cookie);// 요청지(client:브라우저 컴) 쿠키 저장
			} else {
				cookie = new Cookie("c_id", ""); // 쿠키 삭제
				cookie.setMaxAge(0);
				response.addCookie(cookie);

				cookie = new Cookie("c_id_val", "");// 쿠키 삭제
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		if (cnt > 0) { // 로그인
			if (map.get("contentsno") != null && !map.get("contentsno").equals("")) {
				return "redirect:/contents/detail/" + map.get("contentsno");
			} else if (map.get("cateno") != null && !map.get("cateno").equals("")) {
				return "redirect:/contents/mainlist/" + map.get("cateno");
			} else {
				return "redirect:/";
			}
		} else { // 정보를 잘 못 입력 했을 때
			model.addAttribute("msg", "아이디 또는 비밀번호를 잘못 입력 했거나 <br>회원이 아닙니다. 회원가입 하세요");
			return "passwdError";
		}
	}

	/*
	 * //아이디 찾기
	 * 
	 * @PostMapping("/findId")
	 * 
	 * @ResponseBody public String findId(@RequestParam Map<String, String> map) {
	 * // 아이디 찾기 서비스 로직 String id = return id != null ? id : "아이디를 찾을 수 없습니다."; }
	 * 
	 * @PostMapping("/findPassword")
	 * 
	 * @ResponseBody public String findPassword(@RequestBody Map<String, Object>
	 * params) { // 비밀번호 찾기 서비스 로직 // 비밀번호를 반환하는 대신에, 재설정 링크를 이메일로 보내는 로직 구현 boolean
	 * sent = mapper.sendPasswordResetLink(params); return sent ?
	 * "비밀번호 재설정 링크를 이메일로 보냈습니다." : "사용자를 찾을 수 없습니다."; }
	 */

	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션 값 모두 삭제

		return "redirect:/";
	}

	// 회원 목록
	@RequestMapping("/admin/member/list")
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
		int recordPerPage = 3;// 한페이지당 보여줄 레코드갯수

		// DB에서 가져올 순번(oracle)-----------------
		// int sno = ((nowPage - 1) * recordPerPage) + 1;
		// int eno = nowPage * recordPerPage;
		// DB에서 가져올 순번(mysql)-----------------
		int sno = (nowPage - 1) * recordPerPage;
		int eno = recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);

		int total = service.total(map);

		List<MemberDTO> list = service.list(map);

		String url = "list";

		String paging = Utility.paging(total, nowPage, recordPerPage, col, word, url);

		// request에 Model사용 결과 담는다
		request.setAttribute("list", list);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("paging", paging);

		return "/member/list";
	}

	// 회원 조회
	@GetMapping("/admin/member/read")
	public String read(String id, Model model) {
		MemberDTO dto = service.read(id);
		model.addAttribute("dto", dto);

		return "/member/read";
	}

	// 회원 정보 수정
	/*
	 * @PostMapping("member/update") public String update(MemberDTO dto, HttpSession
	 * session, RedirectAttributes redirect) { int cnt = service.update(dto); String
	 * id = (String) session.getAttribute("id");
	 * 
	 * // log.info("cnt:"+cnt); // log.info("id:dto"+dto.getId()); //
	 * log.info("id:session"+id);
	 * 
	 * if (cnt == 1 && id.equals("admin")) { redirect.addAttribute("id",
	 * dto.getId()); return "redirect:/admin/member/read"; } else if (cnt == 1 &&
	 * id.equals(dto.getId())) { return "redirect:./mypage"; } else { return
	 * "error"; } }
	 */

	// admin에서 정보 수정
	@GetMapping("/member/update")
	public String update(String id, Model model) {
		MemberDTO dto = service.read(id);
		model.addAttribute("dto", dto);

		return "/member/update";
	}

	@PostMapping("/member/update")
	public String update(MemberDTO dto, Model model, HttpSession session) {
		int cnt = service.update(dto);
		String url = "redirect:/";

		if (session.getAttribute("grade").equals("A")) {
			url = "redirect:/admin/member/list";
		}

		if (cnt > 0) {
			return url;
		} else {
			return "error";
		}

	}

	// 사용자가 정보 수정 할 때
	@GetMapping("/member/update/{id}")
	public String update_cos(@PathVariable String id, Model model) {
		MemberDTO dto = service.read(id);
		model.addAttribute("dto", dto);

		return "/member/update";
	}

	// 사용자가 이미지 수정
	@GetMapping("/member/updateFile")
	public String updateFile() {

		return "/member/updateFile";
	}
	
	@PostMapping("/member/updateFile")
	public String updateFile(MultipartFile fname, String oldfile, String id) {
		// 삭제
		if(oldfile != null && !oldfile.equals("member.jpg")) {
			Utility.deleteFile(UploadMem.getUploadDir(), oldfile);
		}
		
		// 저장
		String filename = Utility.saveFileSpring(fname, UploadMem.getUploadDir());
		
		Map map = new HashMap();
		map.put("id", id);
		map.put("fname", filename);
		
		int cnt = service.updateFile(map);
		
		if(cnt > 0) {
			return "redirect:/member/mypage";
		}else {
			return "error";
		}
	}

	// 회원 탈퇴
	@GetMapping("/member/delete")
	public String delete(@RequestParam Map<String, String> map, RedirectAttributes redirect) {

		boolean flag = false;

		try {
			String id = map.get("id");
			flag = service.delete(id);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (flag) {
			return "redirect:/admin/member/list";
		} else {
			return "error";
		}
	}

	// 회원 테이블, 주문, 주문 상세 테이블
	@GetMapping("/member/mypage")
	public String mypage(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");

		if (id == null) {
			return "redirect:./login/";
		} else {
			MemberDTO dto = service.mypage(id);
			model.addAttribute("dto", dto);

			return "/member/mypage";
		}
	}
}