package com.hylastix.fridgeservice.exceptions.response;

import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestApiErrorResponse {

    private String error;
    private String message;
    private LocalDateTime timestamp;
}
