package com.seedshare.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.seedshare.entity.Flower;

/**
 * Repository Interface for Flower
 * 
 * @author joao.silva
 */
public interface FlowerRepository extends PagingAndSortingRepository<Flower, Long> {

}
