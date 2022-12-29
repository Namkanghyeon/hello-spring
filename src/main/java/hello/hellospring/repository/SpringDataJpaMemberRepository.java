package hello.hellospring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.hellospring.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
	// JpaRepository를 상속받고 있으면 알아서 스프링이 알아서 구현체를 만들어서 스프링 빈으로 자동 등록 -> 구현이 필요없음
	@Override
	Optional<Member> findByName(String name);
	// 메서드 이름(findBy~) 가지고 JPQL로 "select m from Member m where m.name = ?"로 자동으로 번역됨
}
