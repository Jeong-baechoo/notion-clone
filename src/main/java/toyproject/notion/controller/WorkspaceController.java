package toyproject.notion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.notion.dto.WorkspaceDTO;
import toyproject.notion.service.WorkspaceService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    // 워크스페이스 목록 조회
    @GetMapping
    public List<WorkspaceDTO> getAllWorkspaces() {
        return workspaceService.getAllWorkspaces();
    }

    // 특정 워크스페이스 조회
    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceDTO> getWorkspaceById(@PathVariable Long id) {
        WorkspaceDTO workspaceDTO = workspaceService.getWorkspaceById(id);
        return ResponseEntity.ok(workspaceDTO);
    }

    // 워크스페이스 생성
    @PostMapping
    public ResponseEntity<WorkspaceDTO> createWorkspace(@Valid @RequestBody WorkspaceDTO workspaceDTO) {
        WorkspaceDTO createdWorkspace = workspaceService.createWorkspace(workspaceDTO);
        return ResponseEntity.ok(createdWorkspace);
    }

    // 워크스페이스 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<WorkspaceDTO> updateWorkspace(@PathVariable Long id, @Valid @RequestBody WorkspaceDTO workspaceDTO) {
        WorkspaceDTO updatedWorkspace = workspaceService.updateWorkspace(id, workspaceDTO);
        return ResponseEntity.ok(updatedWorkspace);
    }

    // 워크스페이스 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long id) {
        workspaceService.deleteWorkspace(id);
        return ResponseEntity.ok().build();
    }
}
