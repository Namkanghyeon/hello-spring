package hello.hellospring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;

@Configuration
// 컴포넌트 스캔(@Component & @Autowired) 대신 자바 코드로 직접 빈을 등록해주는 방식
// 과거에는 xml로 했지만 요즘은 거의 안 씀
// 주로 정형화된 빈(컨트롤러, 서비스, 레포지토리)은 컴포넌트 스캔을 이용하고,
// 상황에 따라 인터페이스의 구현 클래스를 변경해야하는 경우 직접 등록 (설정 파일에서만 바꿔치기하면 되니까)
public class SpringConfig {

	private DataSource dataSource;

	@Autowired
	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public MemberService memberService() {
		return new MemberService(memberRepository());
	}

	@Bean
	public MemberRepository memberRepository() {
		// 구현체만 갈아끼우면 됨
		// return new MemoryMemberRepository();
		// return new JdbcMemberRepository(dataSource);
		return new JdbcTemplateMemberRepository(dataSource);
	}

}
