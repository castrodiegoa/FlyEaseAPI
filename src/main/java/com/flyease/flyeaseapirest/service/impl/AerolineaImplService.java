package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AerolineaDao;
import com.flyease.flyeaseapirest.model.dao.AeropuertoDao;
import com.flyease.flyeaseapirest.model.dto.AerolineaDto;
import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.service.IAerolineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AerolineaImplService implements IAerolineaService {

    @Autowired
    private AerolineaDao aerolineaDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Aerolinea> listAlll() {
        return (List) aerolineaDao.findAll();
    }

    @Transactional
    @Override
    public Aerolinea save(AerolineaDto aerolineaDto) {
        jdbcTemplate.update("CALL p_insertar_aereolinea(?, ?, ?)",
                aerolineaDto.getNombre(),
                aerolineaDto.getCodigoIATA(),
                aerolineaDto.getCodigoICAO());

        Aerolinea aerolinea = Aerolinea.builder()
                .idAerolinea(aerolineaDto.getIdAerolinea())
                .nombre(aerolineaDto.getNombre())
                .codigoIATA(aerolineaDto.getCodigoIATA())
                .codigoICAO(aerolineaDto.getCodigoICAO())
                .fechaRegistro(aerolineaDto.getFechaRegistro())
                .build();
        return aerolinea;
    }

    @Transactional
    public Aerolinea update(AerolineaDto aerolineaDto) {
        jdbcTemplate.update("CALL p_actualizar_aereolinea(?, ?, ?, ?)",
                aerolineaDto.getIdAerolinea(),
                aerolineaDto.getNombre(),
                aerolineaDto.getCodigoIATA(),
                aerolineaDto.getCodigoICAO());

        Aerolinea aerolinea = Aerolinea.builder()
                .idAerolinea(aerolineaDto.getIdAerolinea())
                .nombre(aerolineaDto.getNombre())
                .codigoIATA(aerolineaDto.getCodigoIATA())
                .codigoICAO(aerolineaDto.getCodigoICAO())
                .fechaRegistro(aerolineaDto.getFechaRegistro())
                .build();
        return aerolinea;
    }

    @Transactional(readOnly = true)
    @Override
    public Aerolinea findById(Integer id) {
        return aerolineaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Aerolinea aerolinea) {
        jdbcTemplate.update("CALL p_eliminar_aereolinea(?)", aerolinea.getIdAerolinea());
    }

    @Override
    public boolean existsById(Integer id) {
        return aerolineaDao.existsById(id);
    }


}
