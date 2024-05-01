package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.AerolineaDao;
import com.flyease.flyeaseapirest.model.dao.CategoriaDao;
import com.flyease.flyeaseapirest.model.dto.AerolineaDto;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.entity.Aerolinea;
import com.flyease.flyeaseapirest.model.entity.Categoria;
import com.flyease.flyeaseapirest.service.IAeropuertoService;
import com.flyease.flyeaseapirest.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaImplService implements ICategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Categoria> listAlll() {
        return (List) categoriaDao.findAll();
    }

    @Transactional
    @Override
    public Categoria save(CategoriaDto categoriaDto) {
        jdbcTemplate.update("CALL p_insertar_categoria(?, ?, ?, ?, ?)",
                categoriaDto.getNombre(),
                categoriaDto.getDescripcion(),
                categoriaDto.getEstadocategoria(),
                categoriaDto.getTarifa(),
                categoriaDto.getComercial());

        Categoria categoria = Categoria.builder()
                .idcategoria(categoriaDto.getIdcategoria())
                .nombre(categoriaDto.getNombre())
                .descripcion(categoriaDto.getDescripcion())
                .estadocategoria(categoriaDto.getEstadocategoria())
                .tarifa(categoriaDto.getTarifa())
                .comercial(categoriaDto.getComercial())
                .fecharegistro(categoriaDto.getFecharegistro())
                .build();
        return categoria;
    }

    @Transactional
    public Categoria update(CategoriaDto categoriaDto) {
        jdbcTemplate.update("CALL p_actualizar_categoria(?, ?, ?, ?, ?, ?)",
                categoriaDto.getIdcategoria(),
                categoriaDto.getNombre(),
                categoriaDto.getDescripcion(),
                categoriaDto.getEstadocategoria(),
                categoriaDto.getTarifa(),
                categoriaDto.getComercial());

        Categoria categoria = Categoria.builder()
                .idcategoria(categoriaDto.getIdcategoria())
                .nombre(categoriaDto.getNombre())
                .descripcion(categoriaDto.getDescripcion())
                .estadocategoria(categoriaDto.getEstadocategoria())
                .tarifa(categoriaDto.getTarifa())
                .comercial(categoriaDto.getComercial())
                .fecharegistro(categoriaDto.getFecharegistro())
                .build();
        return categoria;
    }

    @Transactional(readOnly = true)
    @Override
    public Categoria findById(Integer id) {
        return categoriaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Categoria categoria) {
        jdbcTemplate.update("CALL p_eliminar_categoria(?)", categoria.getIdcategoria());
    }

    @Override
    public boolean existsById(Integer id) {
        return categoriaDao.existsById(id);
    }

}
