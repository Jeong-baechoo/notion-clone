package toyproject.notion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceDTO {
    private Long id;
    private String name;
    private Long ownerId;
}
