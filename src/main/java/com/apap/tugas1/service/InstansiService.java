package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;

public interface InstansiService {
	void addInstansi (InstansiModel instansi);
	void deleteInstansi (InstansiModel instansi);
	void updateInstansi (InstansiModel instansi);
	Optional<InstansiModel> getInstansiById(Long id);
	List<InstansiModel> findAllInstansi();
}
