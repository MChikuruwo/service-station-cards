package zw.co.codehive.userservice.service.iface;

import zw.co.codehive.userservice.model.Login;
import zw.co.codehive.userservice.model.User;

import java.util.Date;
import java.util.List;

public interface LoginService {
    Login add(Login login);

    List<Login> getAll();

    Login getOne(Long id);

    List<Login> findAllByUser(User user);

    List<Login> findAllByDate(Date date);
}