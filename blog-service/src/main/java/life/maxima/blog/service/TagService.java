package life.maxima.blog.service;

import life.maxima.blog.entity.Tag;

import java.util.List;

public interface TagService {

    void create(String name);

    void create(String... names);

    Tag findByName(String tagName);

    List<Tag> findAll();

}
