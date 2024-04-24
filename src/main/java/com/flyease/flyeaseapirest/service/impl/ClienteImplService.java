package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.CategoriaDao;
import com.flyease.flyeaseapirest.model.dao.ClienteDao;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.dto.ClienteDto;
import com.flyease.flyeaseapirest.model.entity.Categoria;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import com.flyease.flyeaseapirest.service.ICategoriaService;
import com.flyease.flyeaseapirest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteImplService implements IClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Cliente> listAlll() {
        return (List) clienteDao.findAll();
    }

    @Transactional
    @Override
    public Cliente save(ClienteDto clienteDto) {
        jdbcTemplate.update("CALL p_insertar_cliente(?, ?, ?, ?, ?, ?)",
                clienteDto.getNumerodocumento(),
                clienteDto.getTipodocumento(),
                clienteDto.getNombres(),
                clienteDto.getApellidos(),
                clienteDto.getCelular(),
                clienteDto.getCorreo());

        Cliente cliente = Cliente.builder()
                .numerodocumento(clienteDto.getNumerodocumento())
                .tipodocumento(clienteDto.getTipodocumento())
                .nombres(clienteDto.getNombres())
                .apellidos(clienteDto.getApellidos())
                .celular(clienteDto.getCelular())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        return cliente;
    }

    @Transactional
    public Cliente update(ClienteDto clienteDto, String numeroDocumentoAntiguo) {
        jdbcTemplate.update("CALL p_actualizar_cliente(?, ?, ?, ?, ?, ?, ?)",
                numeroDocumentoAntiguo,
                clienteDto.getNumerodocumento(),
                clienteDto.getTipodocumento(),
                clienteDto.getNombres(),
                clienteDto.getApellidos(),
                clienteDto.getCelular(),
                clienteDto.getCorreo());

        Cliente cliente = Cliente.builder()
                .numerodocumento(clienteDto.getNumerodocumento())
                .tipodocumento(clienteDto.getTipodocumento())
                .nombres(clienteDto.getNombres())
                .apellidos(clienteDto.getApellidos())
                .celular(clienteDto.getCelular())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        return cliente;
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(String id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Cliente cliente) {
        jdbcTemplate.update("CALL p_eliminar_cliente(?)", cliente.getNumerodocumento());
    }

    @Override
    public boolean existsById(String id) {
        return clienteDao.existsById(id);
    }

}
