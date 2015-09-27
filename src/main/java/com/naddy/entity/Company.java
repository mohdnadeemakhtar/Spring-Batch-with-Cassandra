package com.naddy.entity;

import java.io.Serializable;

import java.util.Map;

import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.datastax.driver.core.DataType;

@Table("company")
public class Company implements Serializable {

    private static final long serialVersionUID = 7279304635831996399L;

    @PrimaryKey
    private Person person;

    @Column("address")
    private String address;

    @CassandraType(type = DataType.Name.MAP, typeArguments = {DataType.Name.TEXT, DataType.Name.TEXT})
    @Column("brand_opinions")
    private Map<String, String> brandOpinions;

    public Map<String, String> getBrandOpinions() {
        return brandOpinions;
    }

    public Person getPerson() {
        return person;
    }

    public String getAddress() {
        return address;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setBrandOpinions(final Map<String, String> brandOpinions) {
        this.brandOpinions = brandOpinions;
    }

}
