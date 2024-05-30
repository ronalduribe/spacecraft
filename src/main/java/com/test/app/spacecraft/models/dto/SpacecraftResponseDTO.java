package com.test.app.spacecraft.models.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SpacecraftResponseDTO {
    private Long id;
    private String name;
    private String series;
    
}
