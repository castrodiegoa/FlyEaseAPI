package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Region;

import java.util.List;

public interface IRegionService {
    List<Region> listAlll();

    Region save(RegionDto region);

    Region update(RegionDto regionDto);

    Region findById(Integer id);

    void delete(Region Region);

    boolean existsById(Integer id);
}
