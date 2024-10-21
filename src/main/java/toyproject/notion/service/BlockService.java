package toyproject.notion.service;

import toyproject.notion.dto.BlockDTO;

import java.util.List;

public interface BlockService {
    List<BlockDTO> getAllBlocks();
    BlockDTO getBlockById(Long id);
    BlockDTO createBlock(BlockDTO blockDTO);
    BlockDTO updateBlock(Long id, BlockDTO blockDTO);
    void deleteBlock(Long id);
}
