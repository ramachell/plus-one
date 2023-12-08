package com.example.plusone.gs25.dao;

import com.example.plusone.gs25.dto.Gs25Dto;

import java.util.List;

public interface Gs25Dao {

	public void save(Gs25Dto dto);

	public void delete(long id);

	public List<Gs25Dto> getList(Gs25Dto dto);

}
