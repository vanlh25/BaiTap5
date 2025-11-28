package vn.iotstar.service;

import java.util.List;
import java.util.Optional;
import vn.iotstar.entity.Video;

public interface VideoService {

    Optional<Video> findById(int videoId);

    List<Video> findAll();

    List<Video> findByCategory(int categoryId);

    List<Video> search(String keyword);

    Video save(Video video); // insert/update

    void delete(int videoId);

    long count();
}
