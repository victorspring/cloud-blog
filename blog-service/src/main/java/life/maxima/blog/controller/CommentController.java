package life.maxima.blog.controller;

import life.maxima.blog.dto.CommentDto;
import life.maxima.blog.entity.Comment;
import life.maxima.blog.repository.CommentRepository;
import life.maxima.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static life.maxima.blog.utils.Sorts.SORT_BY_DT;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<List<CommentDto>> findAll(){
        return ok(commentRepository.findAll(SORT_BY_DT)
                .stream()
                .map(this::fromComment)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> findById(@PathVariable Long commentId){
        return ResponseEntity.ok(fromComment(commentService.findById(commentId)));
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fromComment(commentService.create(commentDto)));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> update(@PathVariable long commentId,
                                             @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(fromComment(commentService.update(commentId, commentDto)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable long postId){
        commentService.delete(postId);
        return noContent().build();
    }

    private CommentDto fromComment(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getCommentId());
        commentDto.setContent(comment.getContent());
        commentDto.setAuthor(comment.getUserId());
        commentDto.setDtCreated(comment.getDtCreated());
        commentDto.setDtUpdated(comment.getDtUpdated());
        commentDto.setPostId(comment.getPost().getPostId());

        return commentDto;
    }


}

