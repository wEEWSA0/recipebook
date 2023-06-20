package com.weewsa.recipebookv3.service.user;

import com.weewsa.recipebookv3.model.user.User;
import com.weewsa.recipebookv3.repository.UserRepository;
import com.weewsa.recipebookv3.service.authenticate.dto.RegisterRequest;
import com.weewsa.recipebookv3.service.user.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void edit(String tokenLogin, RegisterRequest parameters) throws UserNotFound {
        var foundUser = userRepository.findByLogin(tokenLogin);

        if (foundUser.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        var user = foundUser.get();
        user.setLogin(parameters.getLogin());
        user.setName(parameters.getName());
        user.setPassword(passwordEncoder.encode(parameters.getPassword()));
        user.setAboutMe(parameters.getAboutMe());

        userRepository.save(user);
    }

    public Long getIdByLogin(String login) {
        return userRepository.findIdByLogin(login);
    }

    public User getById(Long id) throws UserNotFound {
        var foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        return foundUser.get();
    }

    public User getByLogin(String login) throws UserNotFound {
        var foundUser = userRepository.findByLogin(login);

        if (foundUser.isEmpty()) {
            throw new UserNotFound("User not found");
        }

        return foundUser.get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }
}
