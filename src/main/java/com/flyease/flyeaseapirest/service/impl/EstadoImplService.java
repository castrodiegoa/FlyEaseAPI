package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.EstadoDao;
import com.flyease.flyeaseapirest.model.entity.Estado;
import com.flyease.flyeaseapirest.service.IReadOnlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoImplService implements IReadOnlyService<Estado, Integer> {

    @Autowired
    private EstadoDao estadoDao;

    @Override
    public List<Estado> listAll() {
        return (List) estadoDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Estado findById(Integer id) {
        return estadoDao.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        return estadoDao.existsById(id);
    }

}
