package com.apap.tugas1.controller;

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

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

import java.util.List;

@Controller
public class JabatanController {
	@Autowired
    private JabatanService jabatanService;
    @Autowired
    private JabatanPegawaiService jabatanPegawaiService;

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
    private String addJabatan(Model model){
        JabatanModel jabatan = new JabatanModel();
        return "add-jabatan";
    }

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
    private String addedJabatan (@ModelAttribute JabatanModel jabatan, Model model){
        jabatanService.addJabatan(jabatan);
        model.addAttribute("jabatan", jabatan);
        return "added-jabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
    private String updateJabatan(@RequestParam(value = "idJabatan") String id_jabatan, Model model){
        Long id = Long.parseLong(id_jabatan);
        JabatanModel jabatan = jabatanService.getJabatanById(id).get();
        model.addAttribute("jabatan", jabatan);
        return "update-jabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
    private String updatedJabatan(@ModelAttribute JabatanModel jabatan, Model model){
        Long id = jabatan.getId();
        jabatanService.updateJabatan(id, jabatan);
        model.addAttribute("jabatan", jabatan);
        return "updated-jabatan";
    }

    @PostMapping (value = "/jabatan/hapus")
    private String deleteJabatan(@ModelAttribute JabatanModel jabatan, Model model){
        List<JabatanPegawaiModel> list = jabatanPegawaiService.findAllByJabatanId(jabatan.getId());
        if (list.isEmpty()){
            jabatanService.deleteJabatan(jabatan);
            model.addAttribute("jabatan", jabatan);
            return "delete-jabatan";
        }
        else {
            model.addAttribute("jabatan",jabatan);
            return "delete-warning";
        }
    }

    @GetMapping(value = "/jabatan/view")
    private String viewJabatan(@RequestParam(value="idJabatan") Long id, Model model) {
        JabatanModel jabatan = jabatanService.getJabatanById(id).get();
        model.addAttribute("jabatan", jabatan);
        return "view-jabatan";
    }
    
    @GetMapping(value = "/jabatan/viewall")
    private String viewAllJabatan(Model model){
        List<JabatanModel> allJabatan = jabatanService.getAll();
        model.addAttribute("allJabatan", allJabatan);
        return "view-all";
    }
}
