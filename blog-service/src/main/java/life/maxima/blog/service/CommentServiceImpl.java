package life.maxima.blog.service;

import life.maxima.blog.dto.CommentDto;
import life.maxima.blog.entity.Comment;
import life.maxima.blog.repository.CommentRepository;
import life.maxima.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static life.maxima.blog.utils.ExceptionUtils.NOT_FOUND;

//import static life.maxima.spring.utils.SecurityUtils.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(NOT_FOUND);
    }

    @Override
//    @Secured("ROLE_USER")
    public Comment create(CommentDto commentDto) {
        Comment comment = new Comment();
        if (StringUtils.hasText(commentDto.getContent())) {
            comment.setContent(commentDto.getContent());
        }


        comment.setDtCreated(LocalDateTime.now());
        //TODO
//        comment.setUser(user);

        comment.setPost(postRepository
                .findById(commentDto.getPostId())
                .orElseThrow(NOT_FOUND));

        return commentRepository.save(comment);
    }

//    @Secured("ROLE_USER")
    @Override
    public Comment update(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NOT_FOUND);
//        checkAuthorityOnComment(comment);

        if (StringUtils.hasText(commentDto.getContent())) {
            comment.setContent(commentDto.getContent());
        }


        comment.setDtUpdated(LocalDateTime.now());
        return commentRepository.save(comment);

    }

    @Override
    public void delete(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NOT_FOUND);
//        checkAuthorityOnComment(comment);
        commentRepository.deleteById(commentId);
    }







}
