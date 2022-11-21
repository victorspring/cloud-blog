package life.maxima.blog.controller;

import life.maxima.blog.dto.PostDto;
import life.maxima.blog.entity.Post;
import life.maxima.blog.entity.Tag;
import life.maxima.blog.service.PostService;
import life.maxima.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @Value("${eureka.instance.instanceId}")
    private String instanceId;

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(@RequestParam Optional<String> query){
        log.info("Using instance: " + instanceId);
        return ResponseEntity.ok(postService.findAll(query).stream()
                .map(this::fromPost)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable long postId){
        return ResponseEntity.ok(fromPost(postService.findById(postId)));
    }

    @PostMapping()
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fromPost(postService.create(postDto)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> update(@PathVariable long postId,
                                          @RequestBody PostDto postDto){
        return ResponseEntity.ok(fromPost(postService.update(postId, postDto)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable long postId){
        postService.delete(postId);
        return noContent().build();
    }

    private PostDto fromPost(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getPostId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setTags(post.getTags().stream()
                .map(Tag::getName)
                .sorted()
                .collect(Collectors.joining(" ")));
        postDto.setAuthor(userService.getUsername(post.getUserId()));
        postDto.setDtCreated(post.getDtCreated());
        postDto.setDtUpdated(post.getDtUpdated());

        return postDto;
    }


}
