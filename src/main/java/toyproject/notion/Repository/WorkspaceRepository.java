package toyproject.notion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.notion.domain.Workspace;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
    Optional<Workspace> findByName(String name);
}
