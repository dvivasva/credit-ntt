package com.dvivasva.credit.repository;

import com.dvivasva.credit.entity.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICreditRepository extends ReactiveMongoRepository<Credit, String> {



}
