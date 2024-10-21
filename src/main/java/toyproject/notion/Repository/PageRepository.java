package toyproject.notion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.notion.domain.Page;

import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {
    // 추가적인 쿼리가 필요하다면 여기서 정의
}
