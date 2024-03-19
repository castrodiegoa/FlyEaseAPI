package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.PaisDao;
import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.service.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaisImplService implements IPaisService {

    @Autowired
    private PaisDao paisDao;

    @Override
    public List<Pais> listAlll() {
        return (List) paisDao.findAll();
    }

    @Transactional
    @Override
    public Pais save(PaisDto paisDto) {
        Pais pais = Pais.builder()
                .idPais(paisDto.getIdPais())
                .nombre(paisDto.getNombre())
                .fechaRegistro(paisDto.getFechaRegistro())
                .build();
        return paisDao.save(pais);
    }

    @Transactional(readOnly = true)
    @Override
    public Pais findById(Integer id) {
        return paisDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Pais pais) {
        paisDao.delete(pais);
    }

    @Override
    public boolean existsById(Integer id) {
        return paisDao.existsById(id);
    }

}