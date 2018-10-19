package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.model.ProvinsiModel;

public class ProvinsiServiceImpl implements ProvinsiService {
	
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public List<ProvinsiModel> getAll(){
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel getProvinsiDetailbyId(Long id) {
		return provinsiDb.getOne(id);
	}
}
