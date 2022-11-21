package life.maxima.blog.utils;

import org.springframework.data.domain.Sort;

public class Sorts {

    public static final Sort SORT_BY_DT = Sort.by("dtCreated").descending();

}

