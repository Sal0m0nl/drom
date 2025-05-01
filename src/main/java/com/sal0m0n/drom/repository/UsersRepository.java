package com.sal0m0n.drom.repository;

import com.sal0m0n.drom.repository.user.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);

}
