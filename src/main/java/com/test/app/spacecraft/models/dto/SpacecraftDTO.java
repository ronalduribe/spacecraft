package com.test.app.spacecraft.models.dto;



public class SpacecraftDTO {
    private Long id;
    private String name;
    private String series;
    
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

    public SpacecraftDTO() {}
    
   
}
