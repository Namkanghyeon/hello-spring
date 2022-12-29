package hello.hellospring.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@SpringBootTest
// 스프링 컨테이너와 테스트를 함께 실행 -> 통합 테스트(스프링, DB까지)
@Transactional
// 테스트 시작 전에 트랜잭션을 실행하고, 테스트가 끝나면 트랜잭션을 롤백함
// -> 테스트가 끝나면 디비에 반영이 안되므로 다음 테스트에 영향을 주지 않음
public class MemberServiceIntegrationTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	@Test
	void 회원가입() { // 테스트코드는 그냥 메서드명 한글로 해도 괜찮음 (어차피 실제 코드에 포함되지 않으니까)
		//given
		Member member = new Member();
		member.setName("hello");

		//when
		Long saveId = memberService.join(member);

		//then
		Member findMember = memberService.findOne(saveId).get();
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}

	@Test
	public void 중복회원예외검증() {
		//given
		Member member1 = new Member();
		member1.setName("spring");

		Member member2 = new Member();
		member2.setName("spring");
		//when

		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		//        try {
		//            memberService.join(member2);
		//            fail();
		//        } catch (IllegalStateException e) {
		//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		//        }

		//then
	}

}

