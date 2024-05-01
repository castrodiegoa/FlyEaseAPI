package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AdministradorDao;
import com.flyease.flyeaseapirest.model.dao.EstadoDao;
import com.flyease.flyeaseapirest.model.entity.Administrador;
import com.flyease.flyeaseapirest.model.entity.Estado;
import com.flyease.flyeaseapirest.service.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministradorImplService implements IAdministradorService {

    @Autowired
    private AdministradorDao administradorDao;

    @Override
    public List<Administrador> listAlll() {
        return (List) administradorDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Administrador findById(Integer id) {
        return administradorDao.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        return administradorDao.existsById(id);
    }

}
