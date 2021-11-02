package com.ituwei.app.Repo;

import com.ituwei.app.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
