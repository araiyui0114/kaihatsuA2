package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	//name検索
	List<Users> findByName( String name);
	//name検索
	List<Users> findByEmail(String email);
	//name検索
	List<Users> findByNameAndPassword(String name, String password);
}
