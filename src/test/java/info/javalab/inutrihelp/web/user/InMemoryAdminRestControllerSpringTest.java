package info.javalab.inutrihelp.web.user;


import info.javalab.inutrihelp.UserTestData;
import info.javalab.inutrihelp.model.User;
import info.javalab.inutrihelp.repository.inmemory.InMemoryUserRepository;
import info.javalab.inutrihelp.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static info.javalab.inutrihelp.UserTestData.ADMIN;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
@RunWith(SpringRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(UserTestData.USER_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(1, users.size());
        Assert.assertEquals(ADMIN, users.iterator().next());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        controller.delete(10);
    }
}

