package com.flyease.flyeaseapirest.model.dao;

import com.flyease.flyeaseapirest.model.entity.Ciudad;
import org.springframework.data.repository.CrudRepository;

public interface CiudadDao extends CrudRepository<Ciudad, Integer> {
}
