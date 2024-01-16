package com.kashyap.reg.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kashyap.reg.dto.UserDto;
import com.kashyap.reg.entity.Roles;
import com.kashyap.reg.entity.User;
import com.kashyap.reg.repository.RolesRepository;
import com.kashyap.reg.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userrepository;

	private RolesRepository rolesRepository;

	private PasswordEncoder encoder;

	private ModelMapper modelMapper;

	@Override
	public void saveUser(UserDto dto) {
		User user = new User();
		user.setName(dto.getFirstName() + " " + dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setPassword(encoder.encode(dto.getPassword()));

		Roles role = rolesRepository.findByName("ROLE_ADMIN");
		if (role == null) {
			role = checkRoleExists();
		}
		user.setRoles(Arrays.asList(role));
		userrepository.save(user);

	}

	public UserServiceImpl(UserRepository userrepository, RolesRepository rolesRepository, PasswordEncoder encoder,
			ModelMapper modelMapper) {
		super();
		this.userrepository = userrepository;
		this.rolesRepository = rolesRepository;
		this.encoder = encoder;
		this.modelMapper = modelMapper;
	}

	private Roles checkRoleExists() {
		Roles roles = new Roles();
		roles.setName("ROLE_ADMIN");
		return rolesRepository.save(roles);

	}

	@Override
	public User findUserByEmail(String email) {
		return userrepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAll() {
		List<User> users = userrepository.findAll();
		return users.stream().map(user -> {
			UserDto userDto = modelMapper.map(user, UserDto.class);
			String[] nameParts = user.getName().split(" ");
			userDto.setFirstName(nameParts[0]);
			userDto.setLastName(nameParts[1]);
			return userDto;
		}).collect(Collectors.toList());
	}

	private UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		String[] str = user.getName().split(" ");
		userDto.setFirstName(str[0]);
		userDto.setLastName(str[1]);
		userDto.setEmail(user.getEmail());
		return userDto;
	}
}
