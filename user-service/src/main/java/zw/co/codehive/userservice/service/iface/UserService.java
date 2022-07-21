package zw.co.codehive.userservice.service.iface;

import zw.co.codehive.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String add(User user);

    String update(User user);

    String delete(Long id);

    List<User> getAll();

    User getOne(Long id);

    User authUser(String userName, String password) throws Exception;

    Optional<User> findByEmailAddress(String emailAddress);

    User findByMobileNumber(String mobileNumber);

    boolean verifyToken(String token);

    User getCurrentUser();

    Optional<User> findUserByPrincipal(final String principal);

    User findByUserId(String userId);
}