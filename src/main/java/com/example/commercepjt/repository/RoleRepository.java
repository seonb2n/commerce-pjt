package com.example.commercepjt.repository;

import com.example.commercepjt.common.enums.RoleStatus;
import com.example.commercepjt.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByRoleStatus(RoleStatus roleStatus);

}
