package com.naddy.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.jdbc.core.JdbcTemplate;

import com.naddy.batch.CassandraBatchItemReader;
import com.naddy.batch.CassandraBatchItemWriter;
import com.naddy.batch.CompanyItemProcessor;

import com.naddy.entity.Company;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@Import(CassandraConfiguration.class)
@ComponentScan(basePackages = "com.naddy.batch")
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Company> reader(final DataSource dataSource) {
        final CassandraBatchItemReader<Company> reader = new CassandraBatchItemReader<Company>(Company.class);
        return reader;
    }

    @Bean
    public ItemProcessor<Company, Company> processor() {
        return new CompanyItemProcessor();
    }

    @Bean
    public ItemWriter<Company> writer(final DataSource dataSource) {
        final CassandraBatchItemWriter<Company> writer = new CassandraBatchItemWriter<Company>(Company.class);
        return writer;
    }

    @Bean
    public Job newSchemaJob(final JobBuilderFactory jobs, final Step s1, final JobExecutionListener listener) {
        return jobs.get("newSchemaJob").incrementer(new RunIdIncrementer()).listener(listener).flow(s1).end().build();
    }

    @Bean
    public Step stepOne(final StepBuilderFactory stepBuilderFactory, final ItemReader<Company> reader,
            final ItemWriter<Company> writer, final ItemProcessor<Company, Company> processor) {
        return stepBuilderFactory.get("stepOne").<Company, Company>chunk(100).reader(reader).processor(processor)
                                 .writer(writer).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
