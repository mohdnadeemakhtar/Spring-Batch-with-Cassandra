package com.naddy.repository;

import com.naddy.entity.Company;

import com.naddy.model.Opinions;

public interface CompanyRepositoryCustom {

    Company getCompany(final String firstName, final String lastName);

    Opinions getOpinion(final String firstName, final String lastName);
}
