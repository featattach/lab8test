package lvov.lab8.service;

import lvov.lab8.dto.UserDto;
import lvov.lab8.entity.Role;
import lvov.lab8.repository.RoleRepository;
import lvov.lab8.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void saveUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getFirstName() + " " +  userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();

        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

    }
    @Override
    public User findUserByEmail(String email){ return userRepository.fingByEmail(email);}
    @Override
    public List<UserDto> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user)-> mapToUserDto(user))
                .collect(Collectors.toList());

    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}