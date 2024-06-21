package com.sawan.exam.serviceInterface;

import org.springframework.stereotype.Service;

import com.sawan.exam.models.Role;

@Service
public interface RoleService {
       Role insertRole(Role role);
}
