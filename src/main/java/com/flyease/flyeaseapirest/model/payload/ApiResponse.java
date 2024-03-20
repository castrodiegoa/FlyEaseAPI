package com.flyease.flyeaseapirest.model.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@Builder
public class ApiResponse {

    private String mensaje;
    private Boolean sucess;

    public ApiResponse(String mensaje, Boolean sucess) {
        this.mensaje = mensaje;
        this.sucess = sucess;
    }

}
