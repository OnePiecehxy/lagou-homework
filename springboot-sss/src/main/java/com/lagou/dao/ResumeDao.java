package com.lagou.dao;

import com.lagou.pojo.Resume;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ResumeDao extends JpaRepository<Resume,Integer>, CrudRepository<Resume, Integer> {

}
