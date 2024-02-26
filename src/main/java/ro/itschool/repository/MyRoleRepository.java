package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.MyRole;
import ro.itschool.entity.RoleEnum;

import java.util.Optional;

public interface MyRoleRepository extends JpaRepository<MyRole, Integer> {
  Optional<MyRole> findByName(RoleEnum name);
}
