package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.ClienteDao;
import com.flyease.flyeaseapirest.model.dao.EstadoDao;
import com.flyease.flyeaseapirest.model.dto.ClienteDto;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import com.flyease.flyeaseapirest.model.entity.Estado;
import com.flyease.flyeaseapirest.service.IEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoImplService implements IEstadoService {

    @Autowired
    private EstadoDao estadoDao;

    @Override
    public List<Estado> listAlll() {
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
