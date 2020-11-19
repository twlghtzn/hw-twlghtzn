package com.greenfoxacademy.homeworktwlghtzn.users;

import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.RequestIncorrectException;
import java.util.Optional;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Before
  public void setUp() {
    Mockito.when(userRepository.findById(1L))
        .thenReturn(Optional.empty());
  }

  @Test
  public void whenUserNotInDB_thenThrowException() {

    Assertions.assertThrows(RequestIncorrectException.class, () -> userService.getUserByUserId(1L));
  }
}
