package toyproject.notion.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO {
    private Long id;
    
    private String title;

    private Long workspaceId; // 소속 워크스페이스의 ID
    private Long createdById; // 생성자의 ID
}
