package toyproject.notion.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.notion.Repository.PageRepository;
import toyproject.notion.Repository.UserRepository;
import toyproject.notion.Repository.WorkspaceRepository;
import toyproject.notion.domain.Page;
import toyproject.notion.domain.User;
import toyproject.notion.domain.Workspace;
import toyproject.notion.dto.PageDTO;
import toyproject.notion.exception.ResourceNotFoundException;
import toyproject.notion.service.PageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    private final WorkspaceRepository workspaceRepository;

    private final UserRepository userRepository;

    // 페이지 목록 조회
    @Override
    public List<PageDTO> getAllPages() {
        return pageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 페이지 조회
    @Override
    public PageDTO getPageById(Long id) {
        Page page = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found with id " + id));
        return convertToDTO(page);
    }

    // 페이지 생성
    @Override
    public PageDTO createPage(PageDTO pageDTO) {
        Workspace workspace = workspaceRepository.findById(pageDTO.getWorkspaceId())
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id " + pageDTO.getWorkspaceId()));
        User user = userRepository.findById(pageDTO.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + pageDTO.getCreatedById()));
        Page page = Page.builder()
                .title(pageDTO.getTitle())
                .workspace(workspace)
                .createdBy(user)
                .build();
        Page savedPage = pageRepository.save(page);
        return convertToDTO(savedPage);
    }

    // 페이지 업데이트
    @Override
    public PageDTO updatePage(Long id, PageDTO pageDTO) {
        Page existingPage = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found with id " + id));
        existingPage.setTitle(pageDTO.getTitle());
        if (pageDTO.getWorkspaceId() != null) {
            Workspace workspace = workspaceRepository.findById(pageDTO.getWorkspaceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id " + pageDTO.getWorkspaceId()));
            existingPage.setWorkspace(workspace);
        }
        if (pageDTO.getCreatedById() != null) {
            User user = userRepository.findById(pageDTO.getCreatedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + pageDTO.getCreatedById()));
            existingPage.setCreatedBy(user);
        }
        Page updatedPage = pageRepository.save(existingPage);
        return convertToDTO(updatedPage);
    }

    // 페이지 삭제
    @Override
    public void deletePage(Long id) {
        Page existingPage = pageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Page not found with id " + id));
        pageRepository.delete(existingPage);
    }

    // DTO 변환 메서드
    private PageDTO convertToDTO(Page page) {
        return PageDTO.builder()
                .id(page.getId())
                .title(page.getTitle())
                .workspaceId(page.getWorkspace().getId())
                .createdById(page.getCreatedBy().getId())
                .build();
    }
}
