package com.naddy.model;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.collect.ImmutableSet;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class Opinions {

    private final Brands brands;

    @JsonCreator
    public Opinions(@JsonProperty("brands") final Brands brands) {
        requireNonNull(brands);

        this.brands = brands;

    }

    public Brands getBrands() {
        return brands;
    }

    @Immutable
    public static class Brands {
        private final Set<String> hidden;

        private final Set<String> followed;

        @JsonCreator
        public Brands(@JsonProperty("followed") final Set<String> followed,
                @JsonProperty("hidden") final Set<String> hidden) {

            this.hidden = hidden != null ? hidden : ImmutableSet.<String>of();
            this.followed = followed != null ? followed : ImmutableSet.<String>of();

        }

        public Set<String> getHidden() {
            return hidden;
        }

        public Set<String> getFollowed() {
            return followed;
        }

        @Override
        public String toString() {
            return "Brands{hidden=" + hidden + ", followed=" + followed + '}';
        }
    }

}
