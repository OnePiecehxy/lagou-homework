package com.lagou.dao;

import com.lagou.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ResumeDao extends JpaRepository<Resume,Integer>, JpaSpecificationExecutor<Resume> {

    @Query("from Resume where id=?1")
    public Resume testJpql(Integer id);
}
