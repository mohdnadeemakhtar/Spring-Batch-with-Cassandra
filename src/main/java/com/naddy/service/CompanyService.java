package com.naddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import com.naddy.entity.Company;
import com.naddy.entity.Person;

import com.naddy.model.Opinions;

import com.naddy.repository.CompanyRepository;

@Service("companyService")
public class CompanyService implements CompanyRepository {

    @Autowired
    @Qualifier("companyRepository")
    private CompanyRepository companyRepository;

    @Override
    public <S extends Company> S save(final S s) {
        return null;
    }

    @Override
    public <S extends Company> Iterable<S> save(final Iterable<S> iterable) {
        return companyRepository.save(iterable);
    }

    @Override
    public Company findOne(final Person person) {

        return null;
    }

    @Override
    public boolean exists(final Person person) {
        return false;
    }

    @Override
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Iterable<Company> findAll(final Iterable<Person> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(final Person person) { }

    @Override
    public void delete(final Company company) { }

    @Override
    public void delete(final Iterable<? extends Company> iterable) { }

    @Override
    public void deleteAll() { }

    @Override
    public Company getCompany(final String firstName, final String lastName) {
        final Company company = companyRepository.getCompany(firstName, lastName);
        return company;
    }

    @Override
    public Opinions getOpinion(final String firstName, final String lastName) {
        final Opinions opinion = companyRepository.getOpinion(firstName, lastName);
        return opinion;
    }
}
