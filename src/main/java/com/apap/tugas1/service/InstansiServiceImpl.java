package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public void addInstansi(InstansiModel instansi) {
		instansiDb.save(instansi);
	}

	@Override
	public void deleteInstansi(InstansiModel instansi) {
		instansiDb.delete(instansi);
	}

	@Override
	public void updateInstansi(InstansiModel instansi) {
		instansiDb.save(instansi);
	}

	@Override
	public Optional<InstansiModel> getInstansiById(Long id){
        return instansiDb.findById(id);
    }

	public List<InstansiModel> findAllInstansi() {
		return instansiDb.findAll();
	}
}
