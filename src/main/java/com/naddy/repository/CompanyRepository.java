package com.naddy.repository;

import org.springframework.data.repository.CrudRepository;

import com.naddy.entity.Company;
import com.naddy.entity.Person;

public interface CompanyRepository extends CrudRepository<Company, Person>, CompanyRepositoryCustom { }
