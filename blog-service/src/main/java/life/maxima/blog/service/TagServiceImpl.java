package life.maxima.blog.service;


import life.maxima.blog.entity.Tag;
import life.maxima.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public void create(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagRepository.save(tag);
    }

    @Override
    public void create(String... names) {
        Arrays.stream(names).forEach(this::create);
    }

    @Override
    public Tag findByName(String tagName) {
        Tag tag = tagRepository.findByName(tagName).orElseThrow();
        tag.getPosts().size();

        return tag;
    }


    @Override
    public List<Tag> findAll() {
        List<Tag> tags = tagRepository.findAll();
        for(Tag t : tags){
            t.getPosts().size();
        }

        return tags;
    }


}
