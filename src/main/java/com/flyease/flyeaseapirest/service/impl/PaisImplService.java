package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.PaisDao;
import com.flyease.flyeaseapirest.model.dto.PaisDto;
import com.flyease.flyeaseapirest.model.entity.Pais;
import com.flyease.flyeaseapirest.service.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaisImplService implements IPaisService {

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Pais> listAlll() {
        return (List) paisDao.findAll();
    }

    @Transactional
    @Override
    public Pais save(PaisDto paisDto) {
        jdbcTemplate.update("CALL p_insertar_pais(?)", paisDto.getNombre());
        Pais pais = Pais.builder()
                .idpais(paisDto.getIdpais())
                .nombre(paisDto.getNombre())
                .fecharegistro(paisDto.getFecharegistro())
                .build();
        return pais;
    }

    @Transactional
    public Pais update(PaisDto paisDto) {
        jdbcTemplate.update("CALL p_actualizar_pais(?, ?)", paisDto.getIdpais(), paisDto.getNombre());
        Pais pais = Pais.builder()
                .idpais(paisDto.getIdpais())
                .nombre(paisDto.getNombre())
                .fecharegistro(paisDto.getFecharegistro())
                .build();
        return pais;
    }


    @Transactional(readOnly = true)
    @Override
    public Pais findById(Integer id) {
        return paisDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Pais pais) {
        jdbcTemplate.update("CALL p_eliminar_pais(?)", pais.getIdpais());
    }

    @Override
    public boolean existsById(Integer id) {
        return paisDao.existsById(id);
    }

}
