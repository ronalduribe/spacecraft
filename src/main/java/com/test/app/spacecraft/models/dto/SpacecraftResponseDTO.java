package com.test.app.spacecraft.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpacecraftResponseDTO {
    private Long id;
    private String name;
    private String series;
    
}
