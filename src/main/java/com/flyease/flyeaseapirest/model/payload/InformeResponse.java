package com.flyease.flyeaseapirest.model.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
public class InformeResponse {

    private String mensaje;
    private Boolean success;

    public InformeResponse(String mensaje, Boolean success) {
        this.mensaje = mensaje;
        this.success = success;
    }

}
