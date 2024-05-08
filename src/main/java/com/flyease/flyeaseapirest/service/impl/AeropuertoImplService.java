package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AeropuertoDao;
import com.flyease.flyeaseapirest.model.dto.AeropuertoDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AeropuertoImplService implements ICrudService<Aeropuerto, AeropuertoDto, Integer> {

    @Autowired
    private AeropuertoDao aeropuertoDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Aeropuerto> listAll() {
        return (List) aeropuertoDao.findAll();
    }

    @Transactional
    @Override
    public Aeropuerto save(AeropuertoDto aeropuertoDto) {
        jdbcTemplate.update("CALL p_insertar_aereopuerto(?, ?, ?, ?, ?, ?, ?)",
                aeropuertoDto.getCiudad().getRegion().getPais().getNombre(),
                aeropuertoDto.getCiudad().getRegion().getNombre(),
                aeropuertoDto.getCiudad().getNombre(),
                aeropuertoDto.getCoordenadas().getLatitud(),
                aeropuertoDto.getCoordenadas().getLongitud(),
                aeropuertoDto.getNombre(),
                aeropuertoDto.getCiudad().getImagen());

        Aeropuerto aeropuerto = Aeropuerto.builder()
                .idaereopuerto(aeropuertoDto.getIdaeropuerto())
                .nombre(aeropuertoDto.getNombre())
                .fechaRegistro(aeropuertoDto.getFecharegistro())
                .ciudad(aeropuertoDto.getCiudad())
                .coordenadas(aeropuertoDto.getCoordenadas())
                .build();
        return aeropuerto;
    }

    @Transactional
    public Aeropuerto update(AeropuertoDto aeropuertoDto) {
        jdbcTemplate.update("CALL p_actualizar_aereopuerto(?, ?, ?, ?, ?, ?, ?, ?)",
                aeropuertoDto.getIdaeropuerto(),
                aeropuertoDto.getNombre(),
                aeropuertoDto.getCiudad().getNombre(),
                aeropuertoDto.getCiudad().getRegion().getNombre(),
                aeropuertoDto.getCiudad().getRegion().getPais().getNombre(),
                aeropuertoDto.getCoordenadas().getLatitud(),
                aeropuertoDto.getCoordenadas().getLongitud(),
                aeropuertoDto.getCiudad().getImagen());

        Aeropuerto aeropuerto = Aeropuerto.builder()
                .idaereopuerto(aeropuertoDto.getIdaeropuerto())
                .nombre(aeropuertoDto.getNombre())
                .fechaRegistro(aeropuertoDto.getFecharegistro())
                .ciudad(aeropuertoDto.getCiudad())
                .coordenadas(aeropuertoDto.getCoordenadas())
                .build();
        return aeropuerto;
    }


    @Transactional(readOnly = true)
    @Override
    public Aeropuerto findById(Integer id) {
        return aeropuertoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Aeropuerto aeropuerto) {
        jdbcTemplate.update("CALL p_eliminar_aereopuerto(?)", aeropuerto.getIdaereopuerto());
    }

    @Override
    public boolean existsById(Integer id) {
        return aeropuertoDao.existsById(id);
    }

}
