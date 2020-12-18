package com.cleverit.springboottest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LICENSE_PLATE")
public class LicensePlate {
	
	@Id
	@Column(name = "LP_ID", unique = true, nullable = false, length = 20)
	private String id;
	
	@Column(name = "LP_PATENTE")
	private String patente;
	
	@Column(name = "LP_TIPO_AUTO")
	private String tipoAuto;
	
	@Column(name = "LP_COLOR")
	private String color;

	
	

}
