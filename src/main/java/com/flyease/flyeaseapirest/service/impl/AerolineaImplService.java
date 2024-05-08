package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AerolineaDao;
import com.flyease.flyeaseapirest.model.dto.AerolineaDto;
import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AerolineaImplService implements ICrudService<Aerolinea, AerolineaDto, Integer> {

    @Autowired
    private AerolineaDao aerolineaDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Aerolinea> listAll() {
        return (List) aerolineaDao.findAll();
    }

    @Transactional
    @Override
    public Aerolinea save(AerolineaDto aerolineaDto) {
        jdbcTemplate.update("CALL p_insertar_aereolinea(?, ?, ?)",
                aerolineaDto.getNombre(),
                aerolineaDto.getCodigoiata(),
                aerolineaDto.getCodigoicao());

        Aerolinea aerolinea = Aerolinea.builder()
                .idaereolinea(aerolineaDto.getIdaereolinea())
                .nombre(aerolineaDto.getNombre())
                .codigoiata(aerolineaDto.getCodigoiata())
                .codigoicao(aerolineaDto.getCodigoicao())
                .fecharegistro(aerolineaDto.getFecharegistro())
                .build();
        return aerolinea;
    }

    @Transactional
    public Aerolinea update(AerolineaDto aerolineaDto) {
        jdbcTemplate.update("CALL p_actualizar_aereolinea(?, ?, ?, ?)",
                aerolineaDto.getIdaereolinea(),
                aerolineaDto.getNombre(),
                aerolineaDto.getCodigoiata(),
                aerolineaDto.getCodigoicao());

        Aerolinea aerolinea = Aerolinea.builder()
                .idaereolinea(aerolineaDto.getIdaereolinea())
                .nombre(aerolineaDto.getNombre())
                .codigoiata(aerolineaDto.getCodigoiata())
                .codigoicao(aerolineaDto.getCodigoicao())
                .fecharegistro(aerolineaDto.getFecharegistro())
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
        jdbcTemplate.update("CALL p_eliminar_aereolinea(?)", aerolinea.getIdaereolinea());
    }

    @Override
    public boolean existsById(Integer id) {
        return aerolineaDao.existsById(id);
    }

}
