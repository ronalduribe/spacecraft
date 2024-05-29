package com.test.app.spacecraft.models.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
public class SpacecraftRequestDTO {
   
	@NotBlank
    private String name;
    @NotBlank
    private String series;
    
    public SpacecraftRequestDTO() {}
    
}
