package com.test.app.spacecraft.models.dto;

import lombok.Data;

@Data
public class SpacecraftDTO {
    private Long id;
    private String name;
    private String series;
    
    public SpacecraftDTO() {}
    
    public SpacecraftDTO(Long id, String name, String series) {
		super();
		this.id = id;
		this.name = name;
		this.series = series;
	}
    
	public SpacecraftDTO(String name, String series) {
		super();
		this.name = name;
		this.series = series;
	}
   
}
