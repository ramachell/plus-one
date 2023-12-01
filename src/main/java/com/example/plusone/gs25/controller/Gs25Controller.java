package com.example.plusone.gs25.controller;

import com.example.plusone.gs25.dao.Gs25Dao;
import com.example.plusone.gs25.dto.Gs25Dto;
import com.example.plusone.gs25.service.Gs25Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles requests for the application home page.
 */
@Controller
public class Gs25Controller {

	private Gs25Dao dao;

	@Autowired
	private Gs25Service service;

	@RequestMapping("/gs25/savedb")
	public String savedb() {
		return "savedb.html";
	}

	@RequestMapping("/gs25/save")
	@ResponseBody
	public Map<String, Object> save(Gs25Dto dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "success");
		dao.save(dto);

		return map;
	}

	@RequestMapping("/gs25/list")
	public String getList() {
		service.getList();
		return "gs25/list";
	}

	@PostMapping("/gs25/search")
	public List<Gs25Dto> searchResult(String searchWord){
		return service.search(searchWord);

	}

}
