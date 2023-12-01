package com.example.plusone.gs25.repository;

import com.example.plusone.gs25.entity.gs25Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface gs25Repository extends JpaRepository<gs25Entity, String> {
    gs25Entity findByName(String name);
}
