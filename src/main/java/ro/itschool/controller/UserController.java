package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.MyUser;
import ro.itschool.service.IMyUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final IMyUserService userService;

  @GetMapping("/all/complete")
  public List<MyUser> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/all/username")
  public List<String> getAllUsersUsernames() {
    return userService.getAllUsersUsernames();
  }

  @GetMapping("/all/nickname")
  public List<String> getAllUsersNicknames() {
    return userService.getAllUsersNicknames();
  }

  @PostMapping
  public void createUser(@RequestBody MyUser myUser) {
    userService.createUser(myUser);
  }

}
