package com.kashyap.reg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kashyap.reg.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);

}
