package lvov.lab8.service;

import lvov.lab8.dto.UserDto;
import lvov.lab8.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
