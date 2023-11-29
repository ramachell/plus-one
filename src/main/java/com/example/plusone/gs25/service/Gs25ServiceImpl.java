package com.example.plusone.gs25.service;

import com.example.plusone.gs25.dao.Gs25Dao;
import com.example.plusone.gs25.dto.Gs25Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;

@Service
public class Gs25ServiceImpl implements Gs25Service {

	@Autowired
	private Gs25Dao dao;

	public void getList() {

	}
}
