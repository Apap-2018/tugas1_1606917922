package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.JabatanPegawaiDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
    private JabatanDb jabatanDb;
    @Autowired
    private JabatanPegawaiDb jabatanPegawaiDb;

    public Optional<JabatanModel> getJabatanById(Long id){
        return jabatanDb.findById(id);
    }

    public void updateJabatan(Long id, JabatanModel jabatan){
        JabatanModel jabatanUpdated = jabatanDb.getOne(id);
        jabatanUpdated.setDeskripsi(jabatan.getDeskripsi());
        jabatanUpdated.setGajiPokok(jabatan.getGajiPokok());
        jabatanUpdated.setId(jabatan.getId());
        jabatanUpdated.setNama(jabatan.getNama());
        jabatanDb.save(jabatanUpdated);
    }

    public void addJabatan(JabatanModel jabatan){
        jabatanDb.save(jabatan);
    }

    public void deleteJabatan(JabatanModel jabatan){
        jabatanDb.deleteById(jabatan.getId());
    }

    public List<JabatanModel> getAll(){
        return jabatanDb.findAll();
    }
}
