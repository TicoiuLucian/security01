package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.MyRole;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.RoleEnum;
import ro.itschool.repository.MyRoleRepository;
import ro.itschool.service.IMyUserService;

@Component
@RequiredArgsConstructor
public class RunAtStartup {

  private final MyRoleRepository roleRepository;

  private final IMyUserService userService;

  @EventListener
  public void appReady(ApplicationReadyEvent event) {

    roleRepository.save(new MyRole(RoleEnum.ROLE_USER));
    roleRepository.save(new MyRole(RoleEnum.ROLE_ADMIN));
    roleRepository.save(new MyRole(RoleEnum.ROLE_EMPLOYEE));

    MyUser myUser = new MyUser();
    myUser.setNickname("nickname1");
    myUser.setUsername("username1");
    myUser.setPassword("password1");

    userService.createUser(myUser);

    MyUser admin = new MyUser();
    admin.setNickname("adminy1");
    admin.setUsername("admin1");
    admin.setPassword("admin1");
    var userAdmin = userService.createUser(admin);
    userService.changeUserRole(userAdmin.getId(), RoleEnum.ROLE_ADMIN);

  }
}
