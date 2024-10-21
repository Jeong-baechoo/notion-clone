package toyproject.notion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.notion.domain.Workspace;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByName(String name);
}
