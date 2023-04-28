package com.groot.backend.controller;

import com.groot.backend.dto.request.ArticleDTO;
import com.groot.backend.dto.response.ArticleListDTO;
import com.groot.backend.dto.response.ArticleResponseDTO;
import com.groot.backend.service.ArticleService;
import com.groot.backend.service.S3Service;
import com.groot.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/articles")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final S3Service s3Service;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static Map<String, Object> resultMap = null;

    // 게시글 작성
    @PostMapping()
    public ResponseEntity createArticle(@RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestPart(value = "articleDTO") ArticleDTO articleDTO) throws IOException {
        resultMap = new HashMap<>();
        if(!userService.isExistedId(articleDTO.getUserPK())){
            resultMap.put("result", FAIL);
            resultMap.put("msg","존재하지 않는 사용자입니다.");
            return ResponseEntity.badRequest().body(resultMap);
        }

        // 이미지 업로드
        String[] imgPaths = s3Service.upload(images, "article");

        if(!articleService.createArticle(articleDTO, imgPaths)){
            resultMap.put("result", FAIL);
            resultMap.put("msg","게시물 등록 실패");
            return ResponseEntity.badRequest().body(resultMap);
        }

        resultMap.put("result", SUCCESS);
        resultMap.put("msg","게시물이 등록되었습니다.");
        return ResponseEntity.ok().body(resultMap);
    }


    // 카테고리별 게시글 리스트 조회
    @GetMapping("/category/{category}")
    public ResponseEntity readArticleList(@PathVariable String category, @RequestParam Integer page, Integer size){
        resultMap = new HashMap<>();
        Page<ArticleListDTO> result = articleService.readArticleList(category, page, size);
        resultMap.put("result", SUCCESS);
        resultMap.put("msg","게시글 목록 조회 완료");
        resultMap.put("articles", result);
        return ResponseEntity.ok().body(resultMap);
    }

    // 게시글 수정
    @PutMapping()
    public ResponseEntity updateArticle(@RequestBody ArticleDTO articleDTO){
        resultMap = new HashMap<>();
        // 게시글 존재 여부 확인
        if(!articleService.existedArticleId(articleDTO.getArticleId())){
            resultMap.put("result", FAIL);
            resultMap.put("msg","존재하지 않는 게시글입니다.");
            return ResponseEntity.badRequest().body(resultMap);
        }

        if(!articleService.updateArticle(articleDTO)){
            resultMap.put("result", FAIL);
            resultMap.put("msg","게시글 수정 실패");
            return ResponseEntity.badRequest().body(resultMap);
        }
        resultMap.put("result", SUCCESS);
        resultMap.put("msg","게시글이 수정되었습니다.");
        return ResponseEntity.ok().body(resultMap);
    }

    // 개별 게시글 조회
    @GetMapping("/{articleId}")
    public ResponseEntity readArticle(@PathVariable Long articleId){
        resultMap = new HashMap<>();
        if(!articleService.existedArticleId(articleId)){
            resultMap.put("result", FAIL);
            resultMap.put("msg","게시글이 존재하지 않습니다.");
            return ResponseEntity.badRequest().body(resultMap);
        }

        ArticleResponseDTO articleResponseDTO = articleService.readArticle(articleId);

        if(articleResponseDTO == null){
            resultMap.put("result", FAIL);
            resultMap.put("msg","게시글 조회 실패");
            return ResponseEntity.badRequest().body(resultMap);
        }

        resultMap.put("result", SUCCESS);
        resultMap.put("msg","게시물이 조회되었습니다.");
        resultMap.put("article",articleResponseDTO);
        return ResponseEntity.ok().body(resultMap);
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable Long articleId){
        resultMap = new HashMap<>();
        if(!articleService.existedArticleId(articleId)){
            resultMap.put("result", FAIL);
            resultMap.put("msg","게시글이 존재하지 않습니다.");
            return ResponseEntity.badRequest().body(resultMap);
        }

        articleService.deleteArticle(articleId);
        resultMap.put("result", SUCCESS);
        resultMap.put("msg","게시글이 삭제되었습니다.");
        return ResponseEntity.ok().body(resultMap);
    }

    // 개별 게시글 북마크 등록/해제

    // 게시글 검색

    // 인기태그 조회

    // 나눔 게시글 지역 필터링

    // 댓글 작성

    // 댓글 수정

    // 댓글 삭제

    // 사용자가 나눔 중인 다른 식물 조회

    // 태그 자동완성


}
