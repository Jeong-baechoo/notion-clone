package toyproject.notion.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.notion.Repository.BlockRepository;
import toyproject.notion.Repository.UserRepository;
import toyproject.notion.Repository.PageRepository;
import toyproject.notion.domain.Block;
import toyproject.notion.domain.Page;
import toyproject.notion.domain.User;
import toyproject.notion.dto.BlockDTO;
import toyproject.notion.exception.ResourceNotFoundException;
import toyproject.notion.service.BlockService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;

    private final PageRepository pageRepository;

    private final UserRepository userRepository;

    // 블록 목록 조회
    @Override
    public List<BlockDTO> getAllBlocks() {
        return blockRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 블록 조회
    @Override
    public BlockDTO getBlockById(Long id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Block not found with id " + id));
        return convertToDTO(block);
    }

    // 블록 생성
    @Override
    public BlockDTO createBlock(BlockDTO blockDTO) {
        Page page = pageRepository.findById(blockDTO.getPageId())
                .orElseThrow(() -> new ResourceNotFoundException("Page not found with id " + blockDTO.getPageId()));
        User user = userRepository.findById(blockDTO.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + blockDTO.getCreatedById()));
        Block parentBlock = null;
        if (blockDTO.getParentBlockId() != null) {
            parentBlock = blockRepository.findById(blockDTO.getParentBlockId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Block not found with id " + blockDTO.getParentBlockId()));
        }
        Block block = Block.builder()
                .type(blockDTO.getType())
                .content(blockDTO.getContent())
                .page(page)
                .parentBlock(parentBlock)
                .createdBy(user)
                .build();
        Block savedBlock = blockRepository.save(block);
        return convertToDTO(savedBlock);
    }

    // 블록 업데이트
    @Override
    public BlockDTO updateBlock(Long id, BlockDTO blockDTO) {
        Block existingBlock = blockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Block not found with id " + id));
        existingBlock.setType(blockDTO.getType());
        existingBlock.setContent(blockDTO.getContent());
        if (blockDTO.getPageId() != null) {
            Page page = pageRepository.findById(blockDTO.getPageId())
                    .orElseThrow(() -> new ResourceNotFoundException("Page not found with id " + blockDTO.getPageId()));
            existingBlock.setPage(page);
        }
        if (blockDTO.getParentBlockId() != null) {
            Block parentBlock = blockRepository.findById(blockDTO.getParentBlockId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Block not found with id " + blockDTO.getParentBlockId()));
            existingBlock.setParentBlock(parentBlock);
        }
        if (blockDTO.getCreatedById() != null) {
            User user = userRepository.findById(blockDTO.getCreatedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + blockDTO.getCreatedById()));
            existingBlock.setCreatedBy(user);
        }
        Block updatedBlock = blockRepository.save(existingBlock);
        return convertToDTO(updatedBlock);
    }

    // 블록 삭제
    @Override
    public void deleteBlock(Long id) {
        Block existingBlock = blockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Block not found with id " + id));
        blockRepository.delete(existingBlock);
    }

    // DTO 변환 메서드
    private BlockDTO convertToDTO(Block block) {
        return BlockDTO.builder()
                .id(block.getId())
                .type(block.getType())
                .content(block.getContent())
                .pageId(block.getPage().getId())
                .parentBlockId(block.getParentBlock() != null ? block.getParentBlock().getId() : null)
                .createdById(block.getCreatedBy().getId())
                .build();
    }
}
