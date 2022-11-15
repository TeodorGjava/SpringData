package com.softuni.usersystem.domain.services;

import com.softuni.usersystem.domain.entities.User;
import com.softuni.usersystem.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepsitory;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepsitory = userRepository;
    }

    @Override
    public List<User> getAllUsersByEmailProvider(String provider) {
        return this.userRepsitory.findAllByEmailEndingWith(provider);
    }

    @Override
    public void save(User user) {
        this.userRepsitory.save(user);
    }

    @Override
    public long getCount() {
        return this.userRepsitory.count();
    }

    @Override
    public List<String> getUserNamesAndAgeByAgeRange(int lowBound, int highBound) {
        return this.userRepsitory.findAllByAgeBetweenOrderByAge(lowBound, highBound)
                .stream().map(User::getFullNameAndAge).collect(Collectors.toList());
    }
}
