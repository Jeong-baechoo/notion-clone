package toyproject.notion.service;

import toyproject.notion.dto.WorkspaceDTO;

import java.util.List;

public interface WorkspaceService {
    List<WorkspaceDTO> getAllWorkspaces();
    WorkspaceDTO getWorkspaceById(Long id);
    WorkspaceDTO createWorkspace(WorkspaceDTO workspaceDTO);
    WorkspaceDTO updateWorkspace(Long id, WorkspaceDTO workspaceDTO);
    void deleteWorkspace(Long id);
}
