package zw.co.codehive.transactiondesignpatterns.service;

import zw.co.codehive.transactiondesignpatterns.dto.admin.AdminCreateDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Admin;

import java.util.Optional;

public interface AdminService {

    Admin add(AdminCreateDto adminCreateDto);

    Optional<Admin> getOne(Long id);

    void delete(Long id);

    Optional<Admin> findByUserName(String userName);
}
