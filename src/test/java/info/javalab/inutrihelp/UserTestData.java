package info.javalab.inutrihelp;

import info.javalab.inutrihelp.model.Role;
import info.javalab.inutrihelp.model.User;
import info.javalab.inutrihelp.repository.inmemory.TestMatcher;

import java.util.Collections;
import java.util.Date;

import static info.javalab.inutrihelp.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        return updated;
    }

    public static TestMatcher<User> USER_MATCHER = TestMatcher.of("registered", "roles");
}
