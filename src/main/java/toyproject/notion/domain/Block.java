package toyproject.notion.domain;


import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "block_id")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String content; // 블록의 내용 (예: 텍스트, 이미지 URL 등)

    // 페이지 (ManyToOne) - 소유자 측 (최상위 블록일 경우)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    // 부모 블록 (ManyToOne) - 소유자 측 (재귀적 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_block_id")
    private Block parentBlock;

    // 자식 블록 (OneToMany) - 비소유자 측 (재귀적 관계)
    @OneToMany(mappedBy = "parentBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Block> childBlocks = new HashSet<>();

    // 생성자 (ManyToOne) - 소유자 측
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}

