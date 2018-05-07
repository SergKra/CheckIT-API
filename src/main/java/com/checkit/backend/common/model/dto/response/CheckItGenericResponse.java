package com.checkit.backend.common.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Gleb Dovzhenko on 02.05.2018.
 */
@Data
@AllArgsConstructor
public class CheckItGenericResponse {
    private Integer code;
    private CheckItResponseType responseType;
    private String message;
}
