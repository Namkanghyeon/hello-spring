package hello.hellospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

@Controller
// 스프링이 뜰 때 스프링 컨테이너가 생성되는데, 거기에 @Controller 어노테이션이 있으면 해당 컨트롤러 객체를 생성해서 넣어두고 관리를 시작함
// 이 관리되는 객체를 빈이라고 부름
// 기본적으로 스프링 빈은 싱글톤으로 관리 (딱 하나만 등록됨)

// 원래는 @Component 어노테이션인데 객체의 목적에 따라 @Controller, @Service, @Repository로 나누어서 붙임
// (내부에 @Component를 포함하고 있음)
// @Component 어노테이션이 있으면 컴포넌트 스캔으로 스프링 빈으로 자동 등록됨
// 기본적으로는 main 메서드가 있는 클래스가 존재하는 패키지 하위만 스캔함
public class MemberController {

	private final MemberService memberService;
	// 얘는 여러 객체를 생성할 필요가 없음
	// 하나만 스프링 컨테이너에 등록해서 사용하면 됨

	@Autowired
	// MemberController 객체가 생성될 때(생성자가 호출될 때) Autowired가 붙어있으면 MemberService 빈을 연결을 해줌
	// 이때, MemberService는 스프링 컨테이너에 등록되어 있어야 하므로 @Service 어느테이션이 붙어있어야 함
	// 마찬가지로 MemoryMemberRepository 역시 @Repository 어노테이션을 사용해서 빈으로 등록되어 있어야 함
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/members/new")
	// /members/new 경로로 접속하면 자동으로 GET 요청이 옴
	public String createForm() {
		return "members/createMemberForm";
	}

	@PostMapping("/members/new")
	// form 태그에서 action 속성을 /members/new, method 속성을 post로 설정해 놓으면 POST 요청이 옴
	// input 태그의 name="name" 속성이 서버로 넘어올 때 key가 됨 == MemberForm 클래스의 name 변수에 자동으로 넣어줌
	public String create(MemberForm form){
		Member member = new Member();
		member.setName(form.getName());

		memberService.join(member);

		return "redirect:/";
	}

	@GetMapping("/members")
	public String list(Model model){
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		// model의 attribute로 넣어주면 thymeleaf 엔진이 이 데이터를 $()로 꺼내서 html을 생성함
		return "members/memberList";
	}
}
