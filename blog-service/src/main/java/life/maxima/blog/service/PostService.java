package life.maxima.blog.service;

import life.maxima.blog.dto.PostDto;
import life.maxima.blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post create(PostDto postDto);

    Post findById(long postId);

    Post update(long postId, PostDto postDto);

    void delete(long postId);

    List<Post> findAll(Optional<String> query);

}
