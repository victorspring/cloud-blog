package life.maxima.blog.repository;

import life.maxima.blog.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitle(String title);

    List<Post> findByDtCreatedBetween(LocalDateTime from, LocalDateTime to);

    List<Post> findByContentContainingIgnoreCase(String substring, Sort sort);

    //Find all posts sorted by tag count
    @Query(value = """
            select
            	p.*
            from
            	post p
            		natural join post_tag pt
            	group by p.post_id
            	order by count(*) desc
            """, nativeQuery = true)
    List<Post> findSortedByTagCount();


}
