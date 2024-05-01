package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.CiudadDao;

import com.flyease.flyeaseapirest.model.dto.CiudadDto;

import com.flyease.flyeaseapirest.model.entity.Ciudad;

import com.flyease.flyeaseapirest.service.ICiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CiudadImplService implements ICiudadService {

    @Autowired
    private CiudadDao ciudadDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Ciudad> listAlll() {
        return (List) ciudadDao.findAll();
    }

    @Transactional
    @Override
    public Ciudad save(CiudadDto ciudadDto) {
        jdbcTemplate.update("CALL p_insertar_ciudad(?, ?, ?, ?)",
                ciudadDto.getNombre(),
                ciudadDto.getRegion().getNombre(),
                ciudadDto.getRegion().getPais().getNombre(),
                ciudadDto.getImagen());

        Ciudad ciudad = Ciudad.builder()
                .idciudad(ciudadDto.getIdciudad())
                .nombre(ciudadDto.getNombre())
                .fecharegistro(ciudadDto.getFecharegistro())
                .imagen(ciudadDto.getImagen())
                .region(ciudadDto.getRegion())
                .build();
        return ciudad;
    }

    @Transactional
    public Ciudad update(CiudadDto ciudadDto) {
        jdbcTemplate.update("CALL p_actualizar_ciudad(?, ?, ?, ?)",
                ciudadDto.getIdciudad(),
                ciudadDto.getNombre(),
                ciudadDto.getRegion().getIdregion(),
                ciudadDto.getImagen());

        Ciudad ciudad = Ciudad.builder()
                .idciudad(ciudadDto.getIdciudad())
                .nombre(ciudadDto.getNombre())
                .fecharegistro(ciudadDto.getFecharegistro())
                .imagen(ciudadDto.getImagen())
                .region(ciudadDto.getRegion())
                .build();
        return ciudad;
    }


    @Transactional(readOnly = true)
    @Override
    public Ciudad findById(Integer id) {
        return ciudadDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Ciudad ciudad) {
        jdbcTemplate.update("CALL p_eliminar_ciudad(?)", ciudad.getIdciudad());
    }

    @Override
    public boolean existsById(Integer id) {
        return ciudadDao.existsById(id);
    }
}
