package toyproject.notion.service;

import toyproject.notion.dto.PageDTO;

import java.util.List;

public interface PageService {
    List<PageDTO> getAllPages();
    PageDTO getPageById(Long id);
    PageDTO createPage(PageDTO pageDTO);
    PageDTO updatePage(Long id, PageDTO pageDTO);
    void deletePage(Long id);
}
