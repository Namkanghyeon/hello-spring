package hello.hellospring.repository;

import hello.hellospring.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트들 간의 순서는 보장되지 않음
    // 각 테스트들은 독립적으로 실행되어야함 -> 테스트가 끝나고 나면 데이터를 clear 해줘야함
    // MemoryMemberRepository 클래스에 clearStore() 메서드 추가
    @AfterEach // 각 테스트가 끝나면 한 번씩 실행됨
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("name");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();

        assertThat(result).isEqualTo(member); // opt + enter: 리팩토링 -> static import
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); // shift + f6: 해당 변수가 쓰이는 모든 곳을 찾아서 동시에 변수명 변경 가능
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
