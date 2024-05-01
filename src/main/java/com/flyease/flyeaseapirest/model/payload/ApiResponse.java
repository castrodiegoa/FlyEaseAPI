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
    private Boolean success;

    public ApiResponse(String mensaje, Boolean success) {
        this.mensaje = mensaje;
        this.success = success;
    }

}
