package zw.co.jugaad.userservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.fudzocommons.security.JwtTokenProvider;
import zw.co.jugaad.fudzocommons.util.OtherUtils;
import zw.co.jugaad.userservice.dao.RoleRepository;
import zw.co.jugaad.userservice.dao.UserRepository;
import zw.co.jugaad.userservice.exceptions.*;
import zw.co.jugaad.userservice.model.MyUserPrincipal;
import zw.co.jugaad.userservice.model.Role;
import zw.co.jugaad.userservice.model.User;
import zw.co.jugaad.userservice.service.iface.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public String add(User user) {
        Optional<User> userFromDatabase = userRepository.findUserByEmailAddress(user.getEmailAddress());

        Optional<User> userNameFromDatabase = Optional.ofNullable(userRepository.findUserByUserName(user.getUserName()));

        if (userFromDatabase.isPresent()) throw new UserAlreadyExistsException("User Already exists!");

        if (userNameFromDatabase.isPresent()) throw new UserNameAlreadyExistsException("User name already taken");

        user.setUserId(OtherUtils.generateUserId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "User Created Successfully.";
    }

    @Transactional
    @Override
    public String update(User user) {
        var userFromDatabase = findByMobileNumber(user.getMobileNumber());

        if (userFromDatabase==null) throw new UserNotFoundException("User does not exist");
        // Carry date created timestamp
        user.setCreatedDate(userFromDatabase.getCreatedDate());

        userRepository.save(user);

        return "User has been successfully updated";
    }

    @Transactional
    @Override
    public String delete(Long id) {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent()) {
            throw new UserNotFoundException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
        return "User has been deleted";

    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UsersNotAvailableException("Users not found");
        }
        return users;
    }

    @Override
    public User getOne(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with the ID " + id + " does not exist");
        }
        return user.get();
    }


    @Override
    public User authUser(String userName, String password) throws Exception {
        //First get the user by userName to check if the user exists
        User user = userRepository.findUserByUserName(userName);
        if (user == null) {
            //Display an error that the user with the userName given was not found
            throw new UsernameNotFoundException("User: " + userName + " not found");
        }
        //Check user entered password if it matches hashed password in database
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        //Else return the user if found
        return user;
    }


    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        var user = userRepository.findUserByEmailAddress(emailAddress);

        if (!user.isPresent()) {
            throw new EmailNotFoundException("User with the email " + emailAddress + " not found");
        }
        return user;
    }

    @Override
    public User findByMobileNumber(String mobileNumber) {
        var user = userRepository.findByMobileNumber(mobileNumber);
        if (user == null) {
            throw new MobileNumberNotFoundException("User with the mobile number " + mobileNumber + " not found");
        }
        return user;
    }


    @Override
    public boolean verifyToken(String token) {

        return tokenProvider.validateToken(token);
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws MobileNumberNotFoundException {
        var user = findByMobileNumber(mobileNumber);

        var authenticatedUser = user;

        roleRepository.findByName(authenticatedUser.toString());
        Set<GrantedAuthority> grantedAuthorities = null;
        try {
            user = findByMobileNumber(mobileNumber);
            if (user == null)
                throw new MobileNumberNotFoundException("Mobile Number :" + mobileNumber + " not found.");

            grantedAuthorities = new HashSet<>();
            for (Role role : authenticatedUser.getRole()) {
                String roleName = "ROLE_" + role.getName();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
                grantedAuthorities.add(grantedAuthority);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        if (user == null) {
            throw new MobileNumberNotFoundException("Could not find the user with mobile number " + mobileNumber);

        }

        return new MyUserPrincipal(authenticatedUser, authenticatedUser.getPassword(), grantedAuthorities);
    }

    @Override
    public Optional<User> findUserByPrincipal(final String principal) {

        var user = findByEmailAddress(principal);

        if (user.get().getMobileNumber().equals(principal))
            return Optional.ofNullable(userRepository.findByMobileNumber(principal));

        if (user.get().getUserName().equals(principal))
            return Optional.ofNullable(userRepository.findUserByUserName(principal));

        return Optional.empty();
    }

    @Override
    public User findByUserId(String userId) {
        var user = userRepository.findByUserId(userId);

        if (user.isEmpty()) throw new UserNotFoundException("user.with.id: "+ userId + " not found");

        return user.get();
    }

    @Override
    public User getCurrentUser() {
//        return findUserByPrincipal(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
//                .orElseThrow(() -> new UserAuthenticationErrorException("current.user.is.not.authenticated.properly"));

        return findUserByPrincipal(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())
                .orElseThrow(() -> new UserAuthenticationErrorException("current.user.is.not.authenticated.properly"));
    }
}