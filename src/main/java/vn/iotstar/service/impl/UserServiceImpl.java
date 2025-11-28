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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username); // Repository trả Optional<User>
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
    public void deleteByUserName(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        userOpt.ifPresent(userRepository::delete);
    }

    @Override
    public Optional<User> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public boolean register(String email, String username, String fullName, String password, String phone) {
        if (userRepository.existsByEmail(email) || userRepository.existsByUsername(username) || userRepository.existsByPhone(phone)) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setFullname(fullName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAdmin(false);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public boolean checkRoleAdmin(String username) {
        return userRepository.findByUsernameAndAdminTrue(username).isPresent();
    }

    @Override
    @Transactional
    public boolean editPassword(String email, String newPassword) {
        int updated = userRepository.updatePasswordByEmail(email, newPassword);
        return updated > 0;
    }
}
