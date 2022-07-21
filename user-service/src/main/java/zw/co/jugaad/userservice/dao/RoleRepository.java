package zw.co.jugaad.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.jugaad.fudzocommons.enums.RoleName;
import zw.co.jugaad.userservice.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    RoleName findByName(String name);
    Role findByName(String name);
}
