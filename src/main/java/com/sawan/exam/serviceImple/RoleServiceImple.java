package com.sawan.exam.serviceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sawan.exam.models.Role;
import com.sawan.exam.repository.RoleRepository;
import com.sawan.exam.serviceInterface.RoleService;

@Service
public class RoleServiceImple implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Override
	public Role insertRole( Role role) {
		System.out.println("HERE");
		role  = roleRepository.insert(role);
//		role = roleRepository.save(role);
		System.out.println(role);
		return role;
	}

}
