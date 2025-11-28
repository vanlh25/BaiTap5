package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.Video;
import vn.iotstar.repository.VideoRepository;
import vn.iotstar.service.VideoService;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Optional<Video> findById(int videoId) {
        return videoRepository.findById(videoId);
    }

    @Override
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> findByCategory(int categoryId) {
        return videoRepository.findByCategoryCategoryId(categoryId);
    }

    @Override
    public List<Video> search(String keyword) {
        return videoRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteById(int videoId) {
        videoRepository.deleteById(videoId);
    }

    @Override
    public long count() {
        return videoRepository.count();
    }
}
