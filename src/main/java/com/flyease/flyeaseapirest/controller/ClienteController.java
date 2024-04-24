package com.flyease.flyeaseapirest.controller;

import com.flyease.flyeaseapirest.exception.BadRequestException;
import com.flyease.flyeaseapirest.exception.ResourceNotFoundException;
import com.flyease.flyeaseapirest.model.dto.CategoriaDto;
import com.flyease.flyeaseapirest.model.dto.ClienteDto;
import com.flyease.flyeaseapirest.model.entity.Categoria;
import com.flyease.flyeaseapirest.model.entity.Cliente;
import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import com.flyease.flyeaseapirest.model.payload.MensajeResponse;
import com.flyease.flyeaseapirest.service.ICategoriaService;
import com.flyease.flyeaseapirest.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ApiFlyEase/v2")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("clientes")
    public ResponseEntity<?> showAll() {
        List<Cliente> getList = clienteService.listAlll();
        if (getList == null || getList.isEmpty()) {
            throw new ResourceNotFoundException("clientes");
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(getList)
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("clientes/{id}")
    public ResponseEntity<?> showById(@PathVariable String id) {
        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            throw new ResourceNotFoundException("cliente", "id", id);
        }

        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("ok")
                        .sucess(true)
                        .response(ClienteDto.builder()
                                .numerodocumento(cliente.getNumerodocumento())
                                .tipodocumento(cliente.getTipodocumento())
                                .nombres(cliente.getNombres())
                                .apellidos(cliente.getApellidos())
                                .celular(cliente.getCelular())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping("clientes")
    public ResponseEntity<?> create(@RequestBody @Valid ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .sucess(true)
                    .response(ClienteDto.builder()
                            .numerodocumento(clienteSave.getNumerodocumento())
                            .tipodocumento(clienteSave.getTipodocumento())
                            .nombres(clienteSave.getNombres())
                            .apellidos(clienteSave.getApellidos())
                            .celular(clienteSave.getCelular())
                            .correo(clienteSave.getCorreo())
                            .fechaRegistro(clienteSave.getFechaRegistro())
                            .build())
                    .build()
                    , HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @PutMapping("clientes/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid ClienteDto clienteDto, @PathVariable String id) {
        Cliente clienteUpdate = null;
        try {
            if (clienteService.existsById(id)) {
                //clienteDto.setNumerodocumento(id);
                clienteUpdate = clienteService.update(clienteDto, id);
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("Actualizado correctamente")
                        .sucess(true)
                        .response(ClienteDto.builder()
                                .numerodocumento(clienteUpdate.getNumerodocumento())
                                .tipodocumento(clienteUpdate.getTipodocumento())
                                .nombres(clienteUpdate.getNombres())
                                .apellidos(clienteUpdate.getApellidos())
                                .celular(clienteUpdate.getCelular())
                                .correo(clienteUpdate.getCorreo())
                                .fechaRegistro(clienteUpdate.getFechaRegistro())
                                .build())
                        .build()
                        , HttpStatus.CREATED);
            } else {
                throw new ResourceNotFoundException("cliente", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

    @DeleteMapping("clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            Cliente clienteDelete = clienteService.findById(id);
            if (clienteDelete != null) {
                clienteService.delete(clienteDelete);
                return new ResponseEntity<>(ApiResponse.builder()
                        .mensaje("Eliminado correctamente")
                        .sucess(true)
                        .build()
                        , HttpStatus.OK);
            }
            else {
                throw new ResourceNotFoundException("cliente", "id", id);
            }
        } catch (DataAccessException exDt) {
            throw  new BadRequestException(exDt.getMessage());
        }
    }

}
