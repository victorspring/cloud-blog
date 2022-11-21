package life.maxima.blog.repository;


import life.maxima.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String username);

    //Find all tags sorted by post count
    @Query(value = """
            select
            	t.*
            from
            	tag t
            		natural join post_tag pt
            	group by t.tag_id
            	order by count(*) desc
            """, nativeQuery = true)
    List<Tag> findSortedByPostCount();

    List<Tag> findByPosts_postId(Long postId);

}
