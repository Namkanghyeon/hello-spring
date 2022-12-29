package hello.hellospring.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import hello.hellospring.domain.Member;

public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;
	// build.gradle에서 JPA 라이브러리를 import하면 스프링 부트가 EntityManager를 자동으로 생성해서 등록
	// 커넥션 정보, 데이터 소스, ... 모두 내부적으로 가지고 있음

	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);// (조회할 타입, 식별자)
		return Optional.ofNullable(member);
	}

	@Override
	public Optional<Member> findByName(String name) {
		List<Member> result = em.createQuery("select m from Member as m where m.name = :name", Member.class)
			// jpql 문법
			.setParameter("name", name)
			.getResultList();

		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		return em.createQuery("select m from Member as m", Member.class)
			.getResultList();
	}
}
