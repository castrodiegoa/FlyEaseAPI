package com.flyease.flyeaseapirest.service.impl;

import com.flyease.flyeaseapirest.model.dao.BoletoDao;
import com.flyease.flyeaseapirest.model.dto.BoletoDto;
import com.flyease.flyeaseapirest.model.entity.Boleto;
import com.flyease.flyeaseapirest.service.IBoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoletoImplService implements IBoletoService {

    @Autowired
    private BoletoDao boletoDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Boleto> listAlll() {
        return (List) boletoDao.findAll();
    }

    @Transactional
    @Override
    public Boleto save(BoletoDto boletoDto) {
        jdbcTemplate.update("CALL p_insertar_boleto(?, ?, ?, ?)",
                boletoDto.getDescuento(),
                boletoDto.getCliente().getNumerodocumento(),
                boletoDto.getAsiento().getIdasiento(),
                boletoDto.getVuelo().getIdvuelo());

        Boleto boleto = Boleto.builder()
                .idboleto(boletoDto.getIdboleto())
                .precio(boletoDto.getPrecio())
                .descuento(boletoDto.getDescuento())
                .preciototal(boletoDto.getPreciototal())
                .cliente(boletoDto.getCliente())
                .asiento(boletoDto.getAsiento())
                .vuelo(boletoDto.getVuelo())
                .fecharegistro(boletoDto.getFecharegistro())
                .build();
        return boleto;
    }

    @Transactional
    public Boleto update(BoletoDto boletoDto) {
        jdbcTemplate.update("CALL p_actualizar_boleto(?, ?, ?, ?, ?, ?, ?)",
                boletoDto.getIdboleto(),
                boletoDto.getPrecio(),
                boletoDto.getDescuento(),
                boletoDto.getPreciototal(),
                boletoDto.getCliente().getNumerodocumento(),
                boletoDto.getAsiento().getIdasiento(),
                boletoDto.getVuelo().getIdvuelo());

        Boleto boleto = Boleto.builder()
                .idboleto(boletoDto.getIdboleto())
                .precio(boletoDto.getPrecio())
                .descuento(boletoDto.getDescuento())
                .preciototal(boletoDto.getPreciototal())
                .cliente(boletoDto.getCliente())
                .asiento(boletoDto.getAsiento())
                .vuelo(boletoDto.getVuelo())
                .fecharegistro(boletoDto.getFecharegistro())
                .build();
        return boleto;
    }


    @Transactional(readOnly = true)
    @Override
    public Boleto findById(Integer id) {
        return boletoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Boleto boleto) {
        jdbcTemplate.update("CALL p_eliminar_boleto(?)", boleto.getIdboleto());
    }

    @Override
    public boolean existsById(Integer id) {
        return boletoDao.existsById(id);
    }

}
