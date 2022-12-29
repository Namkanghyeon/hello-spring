package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 이 어노테이션이 붙어있어야 JPA에서 ORM 가능
public class Member {

	@Id // PK 설정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY: DB가 알아서 생성해주는 방식
	private Long id;
	// @Column(name = 'username') -> 이렇게 하면 테이블의 username 컬럼과 매핑이 됨
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
