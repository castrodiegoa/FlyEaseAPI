package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AvionDao;
import com.flyease.flyeaseapirest.model.dto.AvionDto;
import com.flyease.flyeaseapirest.model.entity.Avion;
import com.flyease.flyeaseapirest.service.IAvionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AvionImplService implements IAvionService {

    @Autowired
    private AvionDao avionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Avion> listAlll() {
        return (List) avionDao.findAll();
    }

    @Transactional
    @Override
    public Avion save(AvionDto avionDto) {
        jdbcTemplate.update("CALL p_insertar_avion(?, ?, ?, ?, ?, ?, ?, ?)",
                avionDto.getIdAvion(),
                avionDto.getNombre(),
                avionDto.getModelo(),
                avionDto.getFabricante(),
                avionDto.getVelocidadPromedio(),
                avionDto.getCantidadPasajeros(),
                avionDto.getCantidadCarga(),
                avionDto.getAerolinea().getIdAerolinea());

        Avion avion = Avion.builder()
                .idavion(avionDto.getIdAvion())
                .nombre(avionDto.getNombre())
                .modelo(avionDto.getModelo())
                .fabricante(avionDto.getFabricante())
                .velocidadPromedio(avionDto.getVelocidadPromedio())
                .cantidadPasajeros(avionDto.getCantidadPasajeros())
                .cantidadCarga(avionDto.getCantidadCarga())
                .aerolinea(avionDto.getAerolinea())
                .fechaRegistro(avionDto.getFechaRegistro())
                .build();
        return avion;
    }

    @Transactional
    public Avion update(AvionDto avionDto, String IdAvionAntiguo) {
        jdbcTemplate.update("CALL p_actualizar_avion(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                IdAvionAntiguo,
                avionDto.getIdAvion(),
                avionDto.getNombre(),
                avionDto.getModelo(),
                avionDto.getFabricante(),
                avionDto.getVelocidadPromedio(),
                avionDto.getCantidadPasajeros(),
                avionDto.getCantidadCarga(),
                avionDto.getAerolinea().getIdAerolinea());

        Avion avion = Avion.builder()
                .idavion(avionDto.getIdAvion())
                .nombre(avionDto.getNombre())
                .modelo(avionDto.getModelo())
                .fabricante(avionDto.getFabricante())
                .velocidadPromedio(avionDto.getVelocidadPromedio())
                .cantidadPasajeros(avionDto.getCantidadPasajeros())
                .cantidadCarga(avionDto.getCantidadCarga())
                .aerolinea(avionDto.getAerolinea())
                .fechaRegistro(avionDto.getFechaRegistro())
                .build();
        return avion;
    }

    @Transactional(readOnly = true)
    @Override
    public Avion findById(String id) {
        return avionDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Avion avion) {
        jdbcTemplate.update("CALL p_eliminar_avion(?)", avion.getIdavion());
    }

    @Override
    public boolean existsById(String id) {
        return avionDao.existsById(id);
    }

}
