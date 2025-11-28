package vn.iotstar.service;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.Category;

public interface CategoryService {

    Optional<Category> findById(int id);

    List<Category> findAll();

    Optional<Category> findByName(String name);

    long count();

    Category save(Category category); // dùng cho insert/update

    void deleteById(int id);
}
