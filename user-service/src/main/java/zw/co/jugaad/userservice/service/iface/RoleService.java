package zw.co.jugaad.userservice.service.iface;

import zw.co.jugaad.fudzocommons.enums.RoleName;
import zw.co.jugaad.userservice.model.Role;

import java.util.List;

public interface RoleService {
    Role add(Role role);

    List<Role> getAll();

    Role getOne(Long id);

    Role findByName(String name);

    void deleteRole(Long id);
}