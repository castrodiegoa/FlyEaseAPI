package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.RegionDao;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Region;
import com.flyease.flyeaseapirest.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegionImplService implements ICrudService<Region, RegionDto, Integer> {

    @Autowired
    private RegionDao regionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Region> listAll() {
        return (List) regionDao.findAll();
    }

    @Transactional
    @Override
    public Region save(RegionDto regionDto) {
        jdbcTemplate.update("CALL p_insertar_region(?, ?)", regionDto.getNombre(), regionDto.getPais().getNombre());
        Region region = Region.builder()
                .idregion(regionDto.getIdregion())
                .nombre(regionDto.getNombre())
                .fecharegistro(regionDto.getFecharegistro())
                .pais(regionDto.getPais())
                .build();
        return region;
    }

    @Transactional
    public Region update(RegionDto regionDto) {
        jdbcTemplate.update("CALL p_actualizar_region(?, ?, ?)", regionDto.getIdregion(), regionDto.getNombre(), regionDto.getPais().getIdpais());
        Region region = Region.builder()
                .idregion(regionDto.getIdregion())
                .nombre(regionDto.getNombre())
                .fecharegistro(regionDto.getFecharegistro())
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
        jdbcTemplate.update("CALL p_eliminar_region(?)", region.getIdregion());
    }

    @Override
    public boolean existsById(Integer id) {
        return regionDao.existsById(id);
    }

}
