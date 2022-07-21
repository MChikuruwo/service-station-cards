package zw.co.codehive.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.codehive.transactiondesignpatterns.dto.admin.AdminCreateDto;
import zw.co.codehive.transactiondesignpatterns.service.AdminService;
import zw.co.codehive.transactiondesignpatterns.service.UserService;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Admin;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.AdminRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;

    @Override
    public Admin add(AdminCreateDto adminCreateDto) {

        var user = userService.getUserByUserId(adminCreateDto.getUserId());
//        var user = userService.getCurrentActualUser(); //to be implemented wen security is added
        var admin = Admin.builder()
                .userName(adminCreateDto.getUserName())
                .userId(user.getUserId())
                .build();

        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> getOne(Long id) {
        return Optional.ofNullable(adminRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("admin with id: " + id + " not found.")));
    }

    @Override
    public void delete(Long id) {
        var admin = getOne(id);
        adminRepository.delete(admin.get());
    }

    @Override
    public Optional<Admin> findByUserName(String userName) {
        return Optional.ofNullable(adminRepository.findByUserName(userName).
                orElseThrow(() -> new EntityNotFoundException("username: " + userName + " does not exist")));
    }
}