package vn.iotstar.service;

import vn.iotstar.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByName(String userName); 
    List<User> findAll();
    long count();
    User save(User user);
    void deleteByName(String userName);
    
    // Các tính năng login / register / đổi password
    Optional<User> login(String userName, String password);  
    boolean register(String email, String userName, String fullName, String password, String phone);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByPhone(String phone);
    
    boolean checkRoleAdmin(String userName);    
    
    boolean editPassword(String email, String newPassword);
}
