package com.ananya.jobportal.specification;

import com.ananya.jobportal.entity.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Job> hasLocation(String location) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("location")),
                        "%" + location.toLowerCase() + "%"
                );
    }

    public static Specification<Job> hasTitle(String title) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Job> hasExperience(Integer experience) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("experience"),
                        experience
                );
    }
}