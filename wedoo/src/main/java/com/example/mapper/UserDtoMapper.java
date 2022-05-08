package com.example.mapper;

import com.example.dtos.UserDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {

    public Optional<User> mapToUser(UserDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        User user = new User();
        user.setId(dto.getId());
        user.setBalance(dto.getBalance());

        return Optional.of(user);
    }

    public List<User> mapToUsers(List<UserDto> dtos) {

        if (dtos == null) {
            return Collections.emptyList();
        }

        return dtos.stream().map(userDto -> mapToUser(userDto).orElse(null)).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public Optional<UserDto> mapToDto(User user) {
        if (user == null) {
            return Optional.empty();
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setBalance(user.getBalance());

        return Optional.of(dto);
    }

    public List<UserDto> mapToDtos(List<User> users) {

        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream().map(user -> mapToDto(user).orElse(null)).filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

}
