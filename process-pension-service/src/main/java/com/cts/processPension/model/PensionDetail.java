package com.cts.processPension.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Model class for pension details
 * 
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class PensionDetail {

	@Id
	private String name;
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dateOfBirth;
	@Column
	private String pan;
	@Column
	private String pensionType;
	@Column
	private double pensionAmount;

}