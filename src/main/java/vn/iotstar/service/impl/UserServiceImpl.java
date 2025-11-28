package vn.iotstar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteByName(String userName) {
        userRepository.deleteById(userName); // userName là PK
    }

    @Override
    public Optional<User> login(String userName, String password) {
        Optional<User> userOpt = userRepository.findByUserName(userName);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public boolean register(String email, String userName, String fullName, String password, String phone) {
        // Kiểm tra tồn tại trước
        if (existsByEmail(email) || existsByUserName(userName) || existsByPhone(phone)) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setUserName(userName);
        user.setFullName(fullName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAdmin(false);  // mặc định user thường
        user.setActive(true);  // mặc định active
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public boolean checkRoleAdmin(String userName) {
        Optional<User> userOpt = userRepository.findByUserNameAndAdminTrue(userName);
        return userOpt.isPresent();
    }

    @Override
    public boolean editPassword(String email, String newPassword) {
        int rowsAffected = userRepository.updatePasswordByEmail(email, newPassword);
        return rowsAffected > 0;
    }
}
