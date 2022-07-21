package zw.co.jugaad.userservice.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.jugaad.userservice.model.Login;
import zw.co.jugaad.userservice.model.User;

import java.util.Date;
import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    List<Login> findAllByUser(User user);
    List<Login> findAllByDate(Date date);
}
