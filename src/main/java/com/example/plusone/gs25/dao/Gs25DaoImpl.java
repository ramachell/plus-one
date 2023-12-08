package com.example.plusone.gs25.dao;

import com.example.plusone.gs25.dto.Gs25Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Gs25DaoImpl implements Gs25Dao {

//	@Autowired
//	private SqlSession session;

	@Override
	public void save(Gs25Dto dto) {
//		session.insert("gs25.insert", dto);
	}

	@Override
	public void delete(long id) {

	}

	@Override
	public List<Gs25Dto> getList(Gs25Dto dto) {


		return null;
	}


}
