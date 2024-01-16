package com.kashyap.reg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kashyap.reg.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Long>{
	Roles findByName(String name);

}
