package com.pr.rbs.rbs_core.repository;

import org.springframework.data.repository.CrudRepository;

import com.pr.rbs.rbs_core.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	Customer findByMobile(Long mobile);
}
