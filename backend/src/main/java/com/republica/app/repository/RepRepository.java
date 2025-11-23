package com.republica.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.republica.app.model.Rep;

@Repository
public interface RepRepository extends JpaRepository<Rep, Long> {

}
