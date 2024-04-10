package com.example.woogisfree.domain.article.service;

import com.example.woogisfree.domain.article.dto.*;
import com.example.woogisfree.domain.article.entity.Article;
import com.example.woogisfree.domain.article.exception.ArticleNotFoundException;
import com.example.woogisfree.domain.article.repository.ArticleRepository;
import com.example.woogisfree.domain.comment.service.CommentService;
import com.example.woogisfree.domain.user.entity.ApplicationUser;
import com.example.woogisfree.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final CommentService commentService;

    @Override
    @Transactional
    public ArticleResponse save(AddArticleRequest request) {
        ApplicationUser user = userService.findUserById(request.getUserId());
        Article savedArticle = articleRepository.save(request.toEntity(user));
        return new ArticleResponse(savedArticle);
    }

    @Override
    public List<ArticleSummaryResponse> findAll() {
        return articleRepository.findAll().stream()
                .map(article -> new ArticleSummaryResponse(article.getId(), article.getTitle(), article.getContent(), article.getCommentList().size()))
                .collect(Collectors.toList());
    }

    @Override
    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
    }

    @Override
    @Transactional
    public void delete(long articleId) {
        articleRepository.deleteById(articleId);
    }


    //TODO ifpresentorelse 추가
    @Override
    @Transactional
    public ArticleResponse update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("not found : " + id));
        article.update(request.getTitle(), request.getContent());
        return new ArticleResponse(article);
    }
}
