package zw.co.codehive.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.codehive.userservice.dao.RoleRepository;
import zw.co.codehive.userservice.exceptions.RoleAlreadyExistsException;
import zw.co.codehive.userservice.service.iface.RoleService;
import zw.co.codehive.userservice.model.Role;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role add(Role role) {
        //check if role already exists
        Optional<Role> optionalRole = Optional.ofNullable(roleRepository.findByName(role.getName()));

        if (optionalRole.isPresent()) throw new RoleAlreadyExistsException("role.already.exists");

        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getOne(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()){
            throw new EntityNotFoundException("Role not found");
        }
        return role.get();
    }

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null){
            throw new EntityNotFoundException("Role " + name + " not found");
        }
        return role;
    }

    @Override
    public void deleteRole(Long id)  {
        roleRepository.deleteById(id);
    }
}