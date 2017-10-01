package com.epam.cinema.dao;


import com.epam.cinema.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/app-spring-context.xml"})
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private static final User USER = new User();

    @Before
    public void init() {
        USER.setFirstName("Alison");
        USER.setLastName("Brie");
        USER.setBirthday(LocalDate.of(1982, 12, 29));
        USER.setEmail("Alison_Brie@gmail.com");
    }

    @After
    public void after() {
        if (isNull(USER.getId()))
            userDao.remove(USER.getId());
    }

    @Test
    public void save() throws Exception {
        Long id = userDao.save(USER);
        USER.setId(id);

        User user = userDao.getById(id);

        Assert.assertEquals(USER.getFirstName(), user.getFirstName());
        Assert.assertEquals(USER.getLastName(), user.getLastName());
        Assert.assertEquals(USER.getEmail(), user.getEmail());
    }

    @Test
    public void remove() throws Exception {
        Long id = userDao.save(USER);
        USER.setId(id);

        userDao.remove(id);

        Assert.assertNull(userDao.getById(id));
    }

    @Test
    public void getById() throws Exception {
        Long id = userDao.save(USER);
        USER.setId(id);

        User user = userDao.getById(id);

        Assert.assertEquals(USER, user);
    }

    @Test
    public void getByEmail() throws Exception {
        Long id = userDao.save(USER);
        USER.setId(id);

        User user = userDao.getById(id);

        Assert.assertEquals(USER.getEmail(), user.getEmail());
    }

    @Test
    public void getAll() throws Exception {
        Long id = userDao.save(USER);
        USER.setId(id);

        Assert.assertTrue(userDao.getAll().size() > 0);
    }

}