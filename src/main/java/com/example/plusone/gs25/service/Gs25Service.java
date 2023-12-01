package com.example.plusone.gs25.service;

import com.example.plusone.gs25.dto.Gs25Dto;

import java.util.List;

public interface Gs25Service {

	public void getList();

	List<Gs25Dto> search(String searchWord);
}
