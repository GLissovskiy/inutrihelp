package info.javalab.inutrihelp.repository.inmemory;

import org.springframework.stereotype.Repository;
import info.javalab.inutrihelp.UserTestData;
import info.javalab.inutrihelp.model.User;
import info.javalab.inutrihelp.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import static info.javalab.inutrihelp.UserTestData.ADMIN;
import static info.javalab.inutrihelp.UserTestData.USER;

@org.springframework.stereotype.Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public void init() {
        map.clear();
        map.put(UserTestData.USER_ID, USER);
        map.put(UserTestData.ADMIN_ID, ADMIN);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}