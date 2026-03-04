package com.ohss.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohss.model.Examination;


@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long>{

    List<Examination> findBySessionId(Long sessionId);

    List<Examination> findByExaminerId(Long examinerId);

    List<Examination> findBySessionIdAndExaminerId(Long sessionId, Long examinerId);
}
