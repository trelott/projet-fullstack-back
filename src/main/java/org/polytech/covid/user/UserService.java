package org.polytech.covid.user;

import jakarta.annotation.PostConstruct;
import org.polytech.covid.center.CenterNotFoundException;
import org.polytech.covid.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostConstruct
    public void defaultSuperAdmin() {
        Optional<User> superAdmin = this.userRepository.findByEmail("superAdmin@superAdmin.com");
        if(superAdmin.isEmpty()) {
            User newDefaultSuperAdmin = new User();
            newDefaultSuperAdmin.setEmail("superAdmin@superAdmin.com");
            newDefaultSuperAdmin.setPassword(passwordEncoder.encode("plokij"));
            newDefaultSuperAdmin.setFirstname("superadmin");
            newDefaultSuperAdmin.setLastname("SUPERADMIN");
            newDefaultSuperAdmin.setRole(Role.SUPERADMIN);
            register(newDefaultSuperAdmin);
        }
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public List<User> findAllUserByCenter(Integer id) {
        return userRepository.findAllByCenter(id);
    }

    public User findUserById(Integer id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) return user.get();
        else throw new UserNotFoundException();
    }

    public User updateUser(Integer id, User updatedUser) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            updatedUser.setId(user.get().getId());
            return userRepository.save(updatedUser);
        }
        else throw new UserNotFoundException();
    }

    public void deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) userRepository.deleteById(id);
        else throw new UserNotFoundException();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(authRequest.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCenter(registerRequest.getCenter());
        user.setRole(Role.USER);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }

    public AuthResponse register(User user) {
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if(user.isPresent()) return user.get();
        else throw new UserNotFoundException();
    }
}
