package com.seedshare.repository;

import org.springframework.data.repository.CrudRepository;

import com.seedshare.entity.Climate;

/**
 * Repository Interface for Climate
 * 
 * @author joao.silva
 */
public interface ClimateRepository extends CrudRepository<Climate, Long> {

}
