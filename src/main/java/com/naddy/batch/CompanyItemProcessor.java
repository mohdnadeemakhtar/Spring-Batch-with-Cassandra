package com.naddy.batch;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.naddy.entity.Company;
import com.naddy.entity.Person;

/**
 * Created by makhtar on 25/09/15.
 */
public class CompanyItemProcessor implements ItemProcessor<Company, Company> {

    private static final Logger log = LoggerFactory.getLogger(CompanyItemProcessor.class);

    private Integer increment = 0;

    @Override
    public Company process(final Company company) throws Exception {
        log.debug("Transformation is data performing, Object is {}", company);

        final Map<String, String> brandMap = newHashMap();

        final Company transformerCompany = new Company();
        transformerCompany.setPerson(new Person(company //
                .getPerson()                 //
                .getFirstName() + increment, //
                company.getPerson()          //
                .getLastName() + increment));
        transformerCompany.setAddress(company.getAddress() + increment);
        transformerCompany.setBrandOpinions(company.getBrandOpinions());

        increment++;

        log.info("Converting {} into {}", company.getPerson(), transformerCompany.getPerson());
        return transformerCompany;
    }
}
