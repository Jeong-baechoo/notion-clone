package toyproject.notion.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.notion.dto.BlockDTO;
import toyproject.notion.service.BlockService;

import java.util.List;

@RestController
@RequestMapping("/api/blocks")
@RequiredArgsConstructor
public class BlockController {


    private final BlockService blockService;

    // 블록 목록 조회
    @GetMapping
    public List<BlockDTO> getAllBlocks() {
        return blockService.getAllBlocks();
    }

    // 특정 블록 조회
    @GetMapping("/{id}")
    public ResponseEntity<BlockDTO> getBlockById(@PathVariable Long id) {
        BlockDTO blockDTO = blockService.getBlockById(id);
        return ResponseEntity.ok(blockDTO);
    }

    // 블록 생성
    @PostMapping
    public ResponseEntity<BlockDTO> createBlock(@Valid @RequestBody BlockDTO blockDTO) {
        BlockDTO createdBlock = blockService.createBlock(blockDTO);
        return ResponseEntity.ok(createdBlock);
    }

    // 블록 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<BlockDTO> updateBlock(@PathVariable Long id, @Valid @RequestBody BlockDTO blockDTO) {
        BlockDTO updatedBlock = blockService.updateBlock(id, blockDTO);
        return ResponseEntity.ok(updatedBlock);
    }

    // 블록 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.ok().build();
    }
}
