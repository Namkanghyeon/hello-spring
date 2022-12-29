package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

@Transactional // JPA는 모든 데이터 변경이 트랜잭션 안에서 처리되어야 하기 때문에 서비스 계층에 @Transactional이 필요함
// 스프링은 해당 클래스의 메서드를 실행할 때 트랜잭션을 시작, 메서드가 정상 종료되면 트랜잭션을 커밋
// 런타임 예외가 발생하면 롤백
public class MemberService { // 클래스 선택하고 cmd + shift + t: 테스트 클래스 자동 생성

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// DI는 생성자 주입, 필드 주입, setter 주입 세 가지가 있음
	// 의존 관계가 실행 중에 동적으로 변하는 경우는 거의 없으므로 생성자 주빙으로 권장

	public Long join(Member member) {
		validateDuplicateMember(member); // ctrl + t: 리팩토링 관련, cmd + opt + v: 리턴값으로 변수를 만들어줌

		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
