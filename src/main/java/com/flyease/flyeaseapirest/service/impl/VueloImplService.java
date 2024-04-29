package com.flyease.flyeaseapirest.service.impl;


import com.flyease.flyeaseapirest.model.dao.VueloDao;
import com.flyease.flyeaseapirest.model.dto.CiudadDto;
import com.flyease.flyeaseapirest.model.dto.VueloDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import com.flyease.flyeaseapirest.service.IVueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VueloImplService implements IVueloService {

    @Autowired
    private VueloDao vueloDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Vuelo> listAlll() {
        return (List) vueloDao.findAll();
    }

    @Transactional
    @Override
    public Vuelo save(VueloDto vueloDto) {
        double distancia = calcularDistancia(vueloDto.getAeropuertoDespegue(), vueloDto.getAeropuertoDestino());

        jdbcTemplate.update("CALL p_insertar_vuelo(?, ?, ?, ?, ?, ?, ?, ?)",
                distancia,
                vueloDto.getPrecioVuelo(),
                vueloDto.getTarifaTemporada(),
                vueloDto.getDescuento(),
                vueloDto.getFechayhoradesalida(),
                vueloDto.getAeropuertoDespegue().getIdAeropuerto(),
                vueloDto.getAeropuertoDestino().getIdAeropuerto(),
                vueloDto.getAvion().getIdavion());

        Vuelo vuelo = Vuelo.builder()
                .idVuelo(vueloDto.getIdVuelo())
                .precioVuelo(vueloDto.getPrecioVuelo())
                .tarifaTemporada(vueloDto.getTarifaTemporada())
                .descuento(vueloDto.getDescuento())
                .distanciaRecorrida(vueloDto.getDistanciaRecorrida())
                .fechayhoradesalida(vueloDto.getFechayhoradesalida())
                .fechayhorallegada(vueloDto.getFechayhorallegada())
                .cupo(vueloDto.getCupo())
                .aeropuertoDespegue(vueloDto.getAeropuertoDespegue())
                .aeropuertoDestino(vueloDto.getAeropuertoDestino())
                .estado(vueloDto.getEstado())
                .avion(vueloDto.getAvion())
                .fechaRegistro(vueloDto.getFechaRegistro())
                .build();
        return vuelo;
    }


    private Double calcularDistancia(Aeropuerto despegue, Aeropuerto destino){
        int R = 6371;

        double lat1 = Math.toRadians(despegue.getCoordenadas().getLatitud());
        double lon1 = Math.toRadians(despegue.getCoordenadas().getLongitud());
        double lat2 = Math.toRadians(destino.getCoordenadas().getLatitud());
        double lon2 = Math.toRadians(destino.getCoordenadas().getLongitud());

        double dLat = Math.abs(lat2 - lat1);
        double dLon = Math.abs(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distancia = R * c;

        return distancia;
    }

    @Transactional
    public Vuelo update(VueloDto vueloDto) {
        double distancia = calcularDistancia(vueloDto.getAeropuertoDespegue(), vueloDto.getAeropuertoDestino());

        jdbcTemplate.update("CALL p_actualizar_vuelo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                vueloDto.getIdVuelo(),
                vueloDto.getPrecioVuelo(),
                vueloDto.getTarifaTemporada(),
                vueloDto.getDescuento(),
                distancia,
                vueloDto.getFechayhorallegada(),
                vueloDto.getCupo(),
                vueloDto.getAeropuertoDespegue().getIdAeropuerto(),
                vueloDto.getAeropuertoDestino().getIdAeropuerto(),
                vueloDto.getEstado().getIdEstado(),
                vueloDto.getAvion().getIdavion());

        Vuelo vuelo = Vuelo.builder()
                .idVuelo(vueloDto.getIdVuelo())
                .precioVuelo(vueloDto.getPrecioVuelo())
                .tarifaTemporada(vueloDto.getTarifaTemporada())
                .descuento(vueloDto.getDescuento())
                .distanciaRecorrida(vueloDto.getDistanciaRecorrida())
                .fechayhoradesalida(vueloDto.getFechayhoradesalida())
                .fechayhorallegada(vueloDto.getFechayhorallegada())
                .cupo(vueloDto.getCupo())
                .aeropuertoDespegue(vueloDto.getAeropuertoDespegue())
                .aeropuertoDestino(vueloDto.getAeropuertoDestino())
                .estado(vueloDto.getEstado())
                .avion(vueloDto.getAvion())
                .fechaRegistro(vueloDto.getFechaRegistro())
                .build();
        return vuelo;
    }


    @Transactional(readOnly = true)
    @Override
    public Vuelo findById(Integer id) {
        return vueloDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Vuelo vuelo) {
        jdbcTemplate.update("CALL p_eliminar_vuelo(?)", vuelo.getIdVuelo());
    }

    @Override
    public boolean existsById(Integer id) {
        return vueloDao.existsById(id);
    }

}
