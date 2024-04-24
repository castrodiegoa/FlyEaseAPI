package com.flyease.flyeaseapirest.model.dao;

import com.flyease.flyeaseapirest.model.entity.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaDao extends CrudRepository<Categoria, Integer> {
}
