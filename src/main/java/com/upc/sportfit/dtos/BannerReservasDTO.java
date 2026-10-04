package com.upc.sportfit.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BannerReservasDTO {

    private Integer reservasMes;
    private String deportePopular;
    private String SedePopular;

}
