package toyproject.notion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockDTO {
    private Long id;
    private String type;
    private String content;
    private Long pageId; // 소속 페이지의 ID
    private Long parentBlockId; // 부모 블록의 ID (재귀적 관계)
    private Long createdById; // 생성자의 ID
}
