package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
    private PegawaiDb pegawaiDb;

    @Override
    public PegawaiModel getPegawaiDetailByNip(String nip) {
        return pegawaiDb.findByNip(nip);
    }

    @Override
    public void addPegawai(PegawaiModel pegawai){
        pegawaiDb.save(pegawai);
    }

    @Override
    public List<PegawaiModel> getAllPegawai(){
        return pegawaiDb.findAll();
    }

    @Override
    public double getGajiLengkapByNip(String nip) {
    	PegawaiModel pegawai = this.getPegawaiDetailByNip(nip);
        
    	double gajiLengkap = 0;
        double gajiTerbesar = 0;
        for (JabatanModel jabatan : pegawai.getJabatanList()) {
            if (jabatan.getGajiPokok() > gajiTerbesar) {
                gajiTerbesar = jabatan.getGajiPokok();
            }
        }
        
        gajiLengkap += gajiTerbesar;
        
        double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
        gajiLengkap += (gajiLengkap * presentaseTunjangan/100);
        
        return gajiLengkap;
    }

	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		 PegawaiModel pegawaiUpdated = pegawaiDb.findByNip(nip);
	     pegawaiUpdated.setNama(pegawai.getNama());
	     pegawaiUpdated.setInstansi(pegawai.getInstansi());
	     pegawaiUpdated.setTahunMasuk(pegawai.getTahunMasuk());
	     pegawaiUpdated.setTanggalLahir(pegawai.getTanggalLahir());
	     pegawaiUpdated.setTempatLahir(pegawai.getTempatLahir());
	     pegawaiDb.save(pegawaiUpdated);
	}

}
