package com.flyease.flyeaseapirest.model.dao;

import com.flyease.flyeaseapirest.model.entity.Boleto;
import com.flyease.flyeaseapirest.model.entity.Region;
import org.springframework.data.repository.CrudRepository;

public interface BoletoDao extends CrudRepository<Boleto, Integer> {
}
