package toyproject.notion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.notion.dto.PageDTO;
import toyproject.notion.service.PageService;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    // 페이지 목록 조회
    @GetMapping
    public List<PageDTO> getAllPages() {
        return pageService.getAllPages();
    }

    // 특정 페이지 조회
    @GetMapping("/{id}")
    public ResponseEntity<PageDTO> getPageById(@PathVariable Long id) {
        PageDTO pageDTO = pageService.getPageById(id);
        return ResponseEntity.ok(pageDTO);
    }

    // 페이지 생성
    @PostMapping
                                                  public ResponseEntity<PageDTO> createPage(@Valid @RequestBody PageDTO pageDTO) {
        PageDTO createdPage = pageService.createPage(pageDTO);
        return ResponseEntity.ok(createdPage);
    }

    // 페이지 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<PageDTO> updatePage(@PathVariable Long id, @Valid @RequestBody PageDTO pageDTO) {
        PageDTO updatedPage = pageService.updatePage(id, pageDTO);
        return ResponseEntity.ok(updatedPage);
    }

    // 페이지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Long id) {
        pageService.deletePage(id);
        return ResponseEntity.ok().build();
    }
}
