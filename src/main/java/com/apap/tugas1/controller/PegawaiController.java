package com.apap.tugas1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
    private PegawaiService pegawaiService;
	
    @Autowired
    private JabatanPegawaiService jabatanPegawaiService;
    
    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private InstansiService instansiService;
    
    @RequestMapping("/")
    private String home(Model model) {
        List<JabatanModel> jabatan = jabatanService.getAll();
        model.addAttribute("listJabatan", jabatan);
        model.addAttribute("allInstansi", instansiService.findAllInstansi());
        model.addAttribute("title", "Home");
        return "home";
    }

    @RequestMapping(value = "/pegawai", method = RequestMethod.GET)
    private String viewPegawai(@RequestParam(value = "nip") String nip, Model model){
        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
        List<JabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getJabatanByPegawaiId(nip).get();
        
        double gajiDouble = pegawaiService.getGajiLengkapByNip(nip);
        long gajiPegawai = (new Double(gajiDouble)).longValue();

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("gajiPegawai", gajiPegawai);
        model.addAttribute("jabatanPegawai", jabatanPegawai);
        model.addAttribute("title", "Data Pegawai");
        return "view-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
    private String addPegawai(Model model){
    	List<InstansiModel> allInstansi = instansiService.findAllInstansi();
        model.addAttribute("allInstansi", allInstansi);
    	List<JabatanModel> allJabatan = jabatanService.getAll();
    	model.addAttribute("allJabatan", allJabatan);
    	return "add-pegawai";
    }
    
    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
    private String updateJabatan(@RequestParam(value = "nip") String nip, Model model){
    	PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allJabatan", jabatanService.getAll());
		model.addAttribute("allInstansi", instansiService.findAllInstansi());
        return "update-pegawai";
    }
    
    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
    private String updatedJabatan(@ModelAttribute PegawaiModel pegawai, Model model){
    	String nip = pegawai.getNip();
        pegawaiService.updatePegawai(nip, pegawai);
        model.addAttribute("pegawai", pegawai);
        return "updated-pegawai";
    }


    @RequestMapping(value = "pegawai/tambah", method = RequestMethod.POST)
    private String addedPegawai(ModelAttribute pegawaiModel ){
    	
        return "added-pegawai";
    }
    

	@RequestMapping(value = "/pegawai/termuda-tertua")
	private String termudaTertua(@RequestParam(value = "idInstansi") String id_instansi, Model model){
		Long id = Long.parseLong(id_instansi);
        InstansiModel instansi = instansiService.getInstansiById(id).get();
        
        PegawaiModel termuda = instansi.getPegawaiByTermuda();
        PegawaiModel tertua = instansi.getPegawaiByTertua();
       
		model.addAttribute("tertua", tertua);
		model.addAttribute("termuda", termuda);
		
		return "view-umur";
	}
	
	@RequestMapping("/pegawai/cari")
	private String cari(Model model) {
		List<JabatanModel> allJabatan = jabatanService.getAll();
		List<InstansiModel> allInstansi = instansiService.findAllInstansi();
		
		model.addAttribute("allInstansi",allInstansi);
		model.addAttribute("allJabatan",allJabatan);
		
		return "find-pegawai";
	}

}
