package com.flyease.flyeaseapirest.service;

import com.flyease.flyeaseapirest.model.dto.BoletoDto;
import com.flyease.flyeaseapirest.model.dto.RegionDto;
import com.flyease.flyeaseapirest.model.entity.Boleto;
import com.flyease.flyeaseapirest.model.entity.Region;

import java.util.List;

public interface IBoletoService {

    List<Boleto> listAlll();

    Boleto save(BoletoDto boletoDto);

    Boleto update(BoletoDto boletoDto);

    Boleto findById(Integer id);

    void delete(Boleto boleto);

    boolean existsById(Integer id);

}
