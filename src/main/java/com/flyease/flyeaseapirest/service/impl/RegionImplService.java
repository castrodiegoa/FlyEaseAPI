package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.RegionDao;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegionImplService implements IRegionService {

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Region> listAlll() {
        return (List) regionDao.findAll();
    }

    @Transactional
    @Override
    public Region save(RegionDto regionDto) {
        jdbcTemplate.update("CALL p_insertar_region(?, ?)", regionDto.getNombre(), regionDto.getPais().getNombre());
        Region region = Region.builder()
                .idRegion(regionDto.getIdRegion())
                .nombre(regionDto.getNombre())
                .fechaRegistro(regionDto.getFechaRegistro())
                .pais(regionDto.getPais())
                .build();
        return region;
    }

    @Transactional
    public Region update(RegionDto regionDto) {
        jdbcTemplate.update("CALL p_actualizar_region(?, ?, ?)", regionDto.getIdRegion(), regionDto.getNombre(), regionDto.getPais().getIdPais());
        Region region = Region.builder()
                .idRegion(regionDto.getIdRegion())
                .nombre(regionDto.getNombre())
                .fechaRegistro(regionDto.getFechaRegistro())
                .pais(regionDto.getPais())
                .build();
        return region;
    }


    @Transactional(readOnly = true)
    @Override
    public Region findById(Integer id) {
        return regionDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Region region) {
        jdbcTemplate.update("CALL p_eliminar_region(?)", region.getIdRegion());
    }

    @Override
    public boolean existsById(Integer id) {
        return regionDao.existsById(id);
    }

}
