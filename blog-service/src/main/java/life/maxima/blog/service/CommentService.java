package life.maxima.blog.service;


import life.maxima.blog.dto.CommentDto;
import life.maxima.blog.entity.Comment;

public interface CommentService {

    Comment findById(Long commentId);

    Comment create(CommentDto commentDto);

    Comment update(Long commentId, CommentDto commentDto);

    void delete(long postId);
}
