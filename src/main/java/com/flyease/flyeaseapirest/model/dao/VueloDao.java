package com.flyease.flyeaseapirest.model.dao;

import com.flyease.flyeaseapirest.model.entity.Ciudad;
import com.flyease.flyeaseapirest.model.entity.Vuelo;
import org.springframework.data.repository.CrudRepository;

public interface VueloDao extends CrudRepository<Vuelo, Integer> {
}
