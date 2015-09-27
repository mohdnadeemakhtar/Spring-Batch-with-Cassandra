package com.naddy;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;

import com.google.common.collect.ImmutableSet;

import com.naddy.configuration.BatchConfiguration;

import com.naddy.entity.Company;
import com.naddy.entity.Person;

import com.naddy.model.Opinions;

import com.naddy.repository.CompanyRepository;

@SpringBootApplication
@Import(BatchConfiguration.class)
public class DataMigrationApplication implements CommandLineRunner {
    @Autowired
    private CompanyRepository companyRepository;

    public static void main(final String[] args) {

        SpringApplication.run(DataMigrationApplication.class, args);
    }

    @Override
    public void run(final String... strings) throws Exception {
        final Map<String, String> brandMap = newHashMap();

        final Set<String> followedSet = ImmutableSet.<String>of("yes", "bye", "shy");
        final Set<String> unFollowedSet = ImmutableSet.<String>of("no", "to", "bo");

        final Opinions.Brands brands = new Opinions.Brands(followedSet, unFollowedSet);
        brandMap.put("followed", brands.getFollowed().toString());
        brandMap.put("hidden", brands.getHidden().toString());

        final Opinions opinions = new Opinions(brands);

        final Company company = new Company();
        company.setPerson(new Person("nadeem", "akhtar"));
        company.setAddress("wilhelm Guddorf");
        company.setBrandOpinions(brandMap);
        companyRepository.save(company);

        final Company nadeem = companyRepository.getCompany("nadeem", "akhtar");
        final Opinions op = companyRepository.getOpinion("nadeem", "akhtar");

        System.out.println("company......................" + nadeem.getBrandOpinions());
        System.out.println("Opinions......................" + op.getBrands());
    }
}
