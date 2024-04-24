package com.flyease.flyeaseapirest.model.dao;

import com.flyease.flyeaseapirest.model.entity.Avion;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface AvionDao extends CrudRepository<Avion, String> {

}
