package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
    private PegawaiDb pegawaiDb;
	
	@Autowired
    private ProvinsiDb provinsiDb;

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

	@Override
	public String getNip(PegawaiModel pegawai) {
		String nip = "";
		//kode provinsi
		ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
		nip+=provinsi.getId();
		
		//urutan instansi di provinsi tsb
		int urutanInstansi = provinsi.getInstansiList().indexOf(pegawai.getInstansi()) + 1;
		
		if(urutanInstansi < 10) { nip+="0"+urutanInstansi;}
		else { nip+=urutanInstansi; }
		
		//tanggalLahir pegawai
		//format dateLama = "yyyy-mm-dd"
		String dateLama = pegawai.getTanggalLahir().toString();
		String ddmmyy = dateLama.substring(8) + dateLama.substring(5, 7) + dateLama.substring(2, 4);
		nip+=ddmmyy;
		
		//tahunMasuk
		nip+=pegawai.getTahunMasuk();
		
		//urutanMasuk
		InstansiModel instansi = pegawai.getInstansi();
		int jumlahNipAwalSama=1;
		for(PegawaiModel pegawaiCek : instansi.getPegawaiInstansi()) {
			if(nip.equals(pegawaiCek.getNip().substring(0, 14))) {
				jumlahNipAwalSama+=1;
			}
		}
		
		if(jumlahNipAwalSama < 10) {nip+="0"+jumlahNipAwalSama;}
		else {nip+=jumlahNipAwalSama;}
		
		return nip;
	}

}
