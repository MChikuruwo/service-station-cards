package zw.co.codehive.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.codehive.userservice.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    RoleName findByName(String name);
    Role findByName(String name);
}
