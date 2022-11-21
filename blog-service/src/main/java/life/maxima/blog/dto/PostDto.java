package life.maxima.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import life.maxima.blog.entity.Post;
import life.maxima.blog.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Setter
@Getter
public class PostDto {

    private Long id;
    private String title;
    private String content;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String tags;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dt_created")
    private LocalDateTime dtCreated;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dt_updated")
    private LocalDateTime dtUpdated;

}
