package life.maxima.blog.service;

import life.maxima.blog.dto.PostDto;
import life.maxima.blog.entity.Post;
import life.maxima.blog.entity.Tag;
import life.maxima.blog.repository.PostRepository;
import life.maxima.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static life.maxima.blog.utils.ExceptionUtils.NOT_FOUND;
import static life.maxima.blog.utils.SecurityUtils.*;
import static life.maxima.blog.utils.Sorts.SORT_BY_DT;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    @Override
    @RolesAllowed("USER")
    public Post create(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setTags(parseTags(postDto.getTags()));

        post.setUserId(getCurrentAuthentication().getName());
        post.setDtCreated(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public Post findById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NOT_FOUND);
        post.getTags().size();
        post.getComments().size();

        return post;
    }

    @Override
    @RolesAllowed("USER")
    public Post update(long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(NOT_FOUND);
        checkAuthorityOnPost(post);

        if (StringUtils.hasText(postDto.getTitle())) {
            post.setTitle(postDto.getTitle());
        }

        if (StringUtils.hasText(postDto.getContent())) {
            post.setContent(postDto.getContent());
        }

        if (postDto.getTags() != null) {
            Set<Tag> newTags = parseTags(postDto.getTags());
            post.getTags().removeAll(newTags);
            removeUnusedTags(post);

            post.setTags(newTags);
        }

        post.setDtUpdated(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public void delete(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(NOT_FOUND);
        checkIsAuthorOrAdmin(post);
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> findAll(Optional<String> query) {
        List<Post> posts;
        if (query.isPresent()) {
            posts = postRepository.findByContentContainingIgnoreCase(
                    query.get(), SORT_BY_DT);
        } else {
            posts = postRepository.findAll(SORT_BY_DT);
        }

        posts.forEach(p -> p.getTags().size());
        return posts;
    }

    private void removeUnusedTags(Post post) {
        Set<Tag> unusedTags = post.getTags().stream()
            .filter(t -> t.getPosts().size() == 1)
            .collect(Collectors.toSet());
        if(unusedTags.size() > 0) {
            tagRepository.deleteAll(unusedTags);
        }
    }

    private Set<Tag> parseTags(String tags) {
        if (!StringUtils.hasText(tags)) {
            return new HashSet<>();
        }

        return Arrays.stream(tags.split(" "))
                .filter(StringUtils::hasText)
                .map(tagName -> tagRepository
                        .findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName))))
                .collect(Collectors.toSet());
    }

}
