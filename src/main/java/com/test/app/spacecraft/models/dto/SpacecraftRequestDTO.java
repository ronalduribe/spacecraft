package com.test.app.spacecraft.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class SpacecraftRequestDTO {
   
	@NotNull(message = "field name required")
    private final String name;
    @NotNull(message = "field series required")
    private final String series;
    
}
