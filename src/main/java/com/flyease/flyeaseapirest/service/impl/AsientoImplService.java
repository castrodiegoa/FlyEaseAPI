package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AsientoDao;
import com.flyease.flyeaseapirest.model.dao.RegionDao;
import com.flyease.flyeaseapirest.model.dto.AsientoDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Asiento;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.service.IAsientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsientoImplService implements IAsientoService {

    @Autowired
    private AsientoDao asientoDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Asiento> listAlll() {
        return (List) asientoDao.findAll();
    }

    @Transactional
    @Override
    public Asiento save(AsientoDto asientoDto) {
        jdbcTemplate.update("CALL p_insertar_asiento(?, ?, ?, ?)",
                asientoDto.getPosicion(),
                asientoDto.getDisponibilidad(),
                asientoDto.getCategoria().getIdCategoria(),
                asientoDto.getAvion().getIdavion());

        Asiento asiento = Asiento.builder()
                .idAsiento(asientoDto.getIdAsiento())
                .posicion(asientoDto.getPosicion())
                .disponibilidad(asientoDto.getDisponibilidad())
                .fechaRegistro(asientoDto.getFechaRegistro())
                .avion(asientoDto.getAvion())
                .categoria(asientoDto.getCategoria())
                .build();
        return asiento;
    }

    @Transactional
    public Asiento update(AsientoDto asientoDto) {
        jdbcTemplate.update("CALL p_actualizar_asiento(?, ?, ?, ?, ?)",
                asientoDto.getIdAsiento(),
                asientoDto.getPosicion(),
                asientoDto.getDisponibilidad(),
                asientoDto.getCategoria().getIdCategoria(),
                asientoDto.getAvion().getIdavion());

        Asiento asiento = Asiento.builder()
                .idAsiento(asientoDto.getIdAsiento())
                .posicion(asientoDto.getPosicion())
                .disponibilidad(asientoDto.getDisponibilidad())
                .fechaRegistro(asientoDto.getFechaRegistro())
                .avion(asientoDto.getAvion())
                .categoria(asientoDto.getCategoria())
                .build();
        return asiento;
    }


    @Transactional(readOnly = true)
    @Override
    public Asiento findById(Integer id) {
        return asientoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Asiento asiento) {
        jdbcTemplate.update("CALL p_eliminar_asiento(?)", asiento.getIdAsiento());
    }

    @Override
    public boolean existsById(Integer id) {
        return asientoDao.existsById(id);
    }

}
