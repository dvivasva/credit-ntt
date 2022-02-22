package com.dvivasva.credit.service;

import com.dvivasva.credit.dto.CreditDto;
import com.dvivasva.credit.utils.ParamReport;
import com.dvivasva.credit.entity.Credit;
import com.dvivasva.credit.repository.ICreditRepository;
import com.dvivasva.credit.utils.CreditUtil;
import com.dvivasva.credit.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class CreditService {

    private final static Logger logger= LoggerFactory.getLogger(CreditService.class);

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private  final ICreditRepository iCreditRepository;

    public Flux<CreditDto> read(){
        logger.info("inside methode read");
        return iCreditRepository.findAll().map(CreditUtil::entityToDto);
    }
    public Mono<CreditDto> create(Mono<CreditDto> creditDtoMono){
        logger.info("inside methode create");

       var newCreditDtoMono=creditDtoMono.map(
                p -> {
                    var today = LocalDateTime.now();
                    p.setDateStart(DateUtil.toDate(today));
                    p.setDateLimit(DateUtil.toDate(today.plusDays(15)));
                    return p;
                });

        return newCreditDtoMono.map(CreditUtil::dtoToEntity)
                .flatMap(reactiveMongoTemplate::save)
                .map(CreditUtil::entityToDto);
    }

    public Mono<CreditDto> update(Mono<CreditDto> creditDtoMono, String id) {
        logger.info("inside methode update");
        return iCreditRepository.findById(id)
                .flatMap(p->creditDtoMono.map(CreditUtil::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(iCreditRepository::save)
                .map(CreditUtil::entityToDto);
    }

    public Mono<Void> delete(String id){
        return iCreditRepository.deleteById(id);
    }


    public Mono<CreditDto> findById(String id){
        return  iCreditRepository.findById(id).map(CreditUtil::entityToDto);
    }

    public Flux<CreditDto> reportBetween(Date init, Date end, String productName){
        logger.info("inside methode reportBetween ");
        Query query = new Query();
        query.addCriteria(Criteria.where("dateStart")
                .gte(DateUtil.toLocalDateTime(init))
                .lte(DateUtil.toLocalDateTime(end))
                .and("nameProduct").is(productName));
        return reactiveMongoTemplate.find(query,Credit.class).map(CreditUtil::entityToDto);
    }

    public Flux<CreditDto> reportBetweenInterval(ParamReport paramReport){
        logger.info("inside methode reportBetweenInterval");
        Query query = new Query();
        query.addCriteria(Criteria.where("dateStart")
                .gte(DateUtil.toLocalDateTime(paramReport.getInit()))
                .lte(DateUtil.toLocalDateTime(paramReport.getEnd()))
                .and("nameProduct").is(paramReport.getProductName()));
        return reactiveMongoTemplate.find(query,Credit.class).map(CreditUtil::entityToDto);
    }


}
