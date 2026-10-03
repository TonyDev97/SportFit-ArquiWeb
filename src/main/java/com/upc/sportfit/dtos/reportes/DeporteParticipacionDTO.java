package com.upc.sportfit.dtos.reportes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeporteParticipacionDTO {
    private String deporte;
    private Long cantidad;

}
