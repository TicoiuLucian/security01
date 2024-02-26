package ro.itschool.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.RoleEnum;

import java.util.List;

public interface IMyUserService extends UserDetailsService {

  List<MyUser> getAllUsers();

  List<String> getAllUsersUsernames();

  List<String> getAllUsersNicknames();

  MyUser createUser(final MyUser myUser);

  void changeUserRole(final Integer userId, final RoleEnum roleEnum);
}
