package com.flyease.flyeaseapirest.service.impl;


import com.flyease.flyeaseapirest.model.dao.VueloDao;
import com.flyease.flyeaseapirest.model.dto.VueloDto;
import com.flyease.flyeaseapirest.model.entity.Aeropuerto;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import com.flyease.flyeaseapirest.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VueloImplService implements ICrudService<Vuelo, VueloDto, Integer> {

    @Autowired
    private VueloDao vueloDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Vuelo> listAll() {
        return (List) vueloDao.findAll();
    }

    @Transactional
    @Override
    public Vuelo save(VueloDto vueloDto) {
        double distancia = calcularDistancia(vueloDto.getAeropuerto_Despegue(), vueloDto.getAeropuerto_Destino());

        jdbcTemplate.update("CALL p_insertar_vuelo(?, ?, ?, ?, ?, ?, ?, ?)",
                distancia,
                vueloDto.getPreciovuelo(),
                vueloDto.getTarifatemporada(),
                vueloDto.getDescuento(),
                vueloDto.getFechayhoradesalida(),
                vueloDto.getAeropuerto_Despegue().getIdaereopuerto(),
                vueloDto.getAeropuerto_Destino().getIdaereopuerto(),
                vueloDto.getAvion().getIdavion());

        Vuelo vuelo = Vuelo.builder()
                .idvuelo(vueloDto.getIdvuelo())
                .preciovuelo(vueloDto.getPreciovuelo())
                .tarifatemporada(vueloDto.getTarifatemporada())
                .descuento(vueloDto.getDescuento())
                .distanciarecorrida(vueloDto.getDistanciarecorrida())
                .fechayhoradesalida(vueloDto.getFechayhoradesalida())
                .fechayhorallegada(vueloDto.getFechayhorallegada())
                .cupo(vueloDto.getCupo())
                .aeropuerto_Despegue(vueloDto.getAeropuerto_Despegue())
                .aeropuerto_Destino(vueloDto.getAeropuerto_Destino())
                .estado(vueloDto.getEstado())
                .avion(vueloDto.getAvion())
                .fecharegistro(vueloDto.getFecharegistro())
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
        double distancia = calcularDistancia(vueloDto.getAeropuerto_Despegue(), vueloDto.getAeropuerto_Destino());

        jdbcTemplate.update("CALL p_actualizar_vuelo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                vueloDto.getIdvuelo(),
                vueloDto.getPreciovuelo(),
                vueloDto.getTarifatemporada(),
                vueloDto.getDescuento(),
                distancia,
                vueloDto.getFechayhorallegada(),
                vueloDto.getCupo(),
                vueloDto.getAeropuerto_Despegue().getIdaereopuerto(),
                vueloDto.getAeropuerto_Destino().getIdaereopuerto(),
                vueloDto.getEstado().getIdestado(),
                vueloDto.getAvion().getIdavion());

        Vuelo vuelo = Vuelo.builder()
                .idvuelo(vueloDto.getIdvuelo())
                .preciovuelo(vueloDto.getPreciovuelo())
                .tarifatemporada(vueloDto.getTarifatemporada())
                .descuento(vueloDto.getDescuento())
                .distanciarecorrida(vueloDto.getDistanciarecorrida())
                .fechayhoradesalida(vueloDto.getFechayhoradesalida())
                .fechayhorallegada(vueloDto.getFechayhorallegada())
                .cupo(vueloDto.getCupo())
                .aeropuerto_Despegue(vueloDto.getAeropuerto_Despegue())
                .aeropuerto_Destino(vueloDto.getAeropuerto_Destino())
                .estado(vueloDto.getEstado())
                .avion(vueloDto.getAvion())
                .fecharegistro(vueloDto.getFecharegistro())
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
        jdbcTemplate.update("CALL p_eliminar_vuelo(?)", vuelo.getIdvuelo());
    }

    @Override
    public boolean existsById(Integer id) {
        return vueloDao.existsById(id);
    }

}
