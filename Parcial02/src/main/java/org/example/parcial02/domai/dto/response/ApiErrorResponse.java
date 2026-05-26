package org.example.parcial02.domai.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {
    private Object message;
    private int status;
    private LocalDateTime time;
    private String uri;
}
