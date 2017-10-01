package com.seedshare.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Persistence class for the table MONTH
 * @author joao.silva
 */
@Entity
@Table(name = "MONTH")
public class Month implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final String SEQUENCE_NAME = "MONTH_SEQ";

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
    @Basic(optional = false)
	@Column(name = "MONTH_ID")
	private Long id;
	
	@Basic(optional = false)
	@NotNull
	@Size(max = 25)
	@Column(name = "NAME", length = 25)
	private String name;

	protected Month() {
		
	}
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
