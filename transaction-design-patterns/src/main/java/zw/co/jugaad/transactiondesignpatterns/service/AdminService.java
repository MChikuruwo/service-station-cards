package zw.co.jugaad.transactiondesignpatterns.service;

import zw.co.jugaad.transactiondesignpatterns.dto.admin.AdminCreateDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Admin;

import java.util.Optional;

public interface AdminService {

    Admin add(AdminCreateDto adminCreateDto);

    Optional<Admin> getOne(Long id);

    void delete(Long id);

    Optional<Admin> findByUserName(String userName);
}
