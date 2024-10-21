package toyproject.notion.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.notion.Repository.UserRepository;
import toyproject.notion.Repository.WorkspaceRepository;
import toyproject.notion.domain.User;
import toyproject.notion.domain.Workspace;
import toyproject.notion.dto.WorkspaceDTO;
import toyproject.notion.exception.ResourceNotFoundException;
import toyproject.notion.service.WorkspaceService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserRepository userRepository;

    // 워크스페이스 목록 조회
    @Override
    public List<WorkspaceDTO> getAllWorkspaces() {
        return workspaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 워크스페이스 조회
    @Override
    public WorkspaceDTO getWorkspaceById(Long id) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id " + id));
        return convertToDTO(workspace);
    }

    // 워크스페이스 생성
    @Override
    public WorkspaceDTO createWorkspace(WorkspaceDTO workspaceDTO) {
        User owner = userRepository.findById(workspaceDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + workspaceDTO.getOwnerId()));
        Workspace workspace = Workspace.builder()
                .name(workspaceDTO.getName())
                .owner(owner)
                .build();
        Workspace savedWorkspace = workspaceRepository.save(workspace);
        return convertToDTO(savedWorkspace);
    }

    // 워크스페이스 업데이트
    @Override
    public WorkspaceDTO updateWorkspace(Long id, WorkspaceDTO workspaceDTO) {
        Workspace existingWorkspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id " + id));
        existingWorkspace.setName(workspaceDTO.getName());
        if (workspaceDTO.getOwnerId() != null) {
            User owner = userRepository.findById(workspaceDTO.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + workspaceDTO.getOwnerId()));
            existingWorkspace.setOwner(owner);
        }
        Workspace updatedWorkspace = workspaceRepository.save(existingWorkspace);
        return convertToDTO(updatedWorkspace);
    }

    // 워크스페이스 삭제
    @Override
    public void deleteWorkspace(Long id) {
        Workspace existingWorkspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id " + id));
        workspaceRepository.delete(existingWorkspace);
    }

    // DTO 변환 메서드
    private WorkspaceDTO convertToDTO(Workspace workspace) {
        return WorkspaceDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .ownerId(workspace.getOwner().getId())
                .build();
    }
}
