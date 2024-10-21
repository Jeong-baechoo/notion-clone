package toyproject.notion.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "page_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    // 워크스페이스 (ManyToOne) - 소유자 측
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    // 생성자 (ManyToOne) - 소유자 측
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // 관련 블록 (1:N) - 비소유자 측
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Block> blocks = new HashSet<>();
}
