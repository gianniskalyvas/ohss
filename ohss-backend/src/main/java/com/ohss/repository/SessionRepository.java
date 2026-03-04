package com.ohss.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohss.model.Session;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
