package vn.iotstar.service;

import vn.iotstar.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username); 
    List<User> findAll();
    long count();
    User save(User user);
    void deleteByUserName(String username);
    Optional<User> login(String username, String password);
    boolean register(String email, String username, String fullName, String password, String phone);
    boolean checkExistEmail(String email);
    boolean checkExistUsername(String username);
    boolean checkExistPhone(String phone);
    boolean checkRoleAdmin(String username);
    boolean editPassword(String email, String newPassword);
}
