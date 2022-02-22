package com.dvivasva.credit.controller;

import com.dvivasva.credit.dto.CreditDto;
import com.dvivasva.credit.utils.ParamReport;
import com.dvivasva.credit.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/credit")
public class CreditController {
    private final CreditService creditService;

    @GetMapping
    public Flux<CreditDto> read() {
        return this.creditService.read();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CreditDto> create(@RequestBody Mono<CreditDto> creditDtoMono) {
        return this.creditService.create(creditDtoMono);
    }
    @PutMapping("/{id}")
    public Mono<CreditDto> update( @RequestBody Mono<CreditDto> creditDtoMono,@PathVariable String id){
        return this.creditService.update(creditDtoMono,id);
    }
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return this.creditService.delete(id);
    }

    @GetMapping("/{id}")
    public Mono<CreditDto> findById(@PathVariable String id){
        return creditService.findById(id);
    }

    @GetMapping("/report-between")
    public Flux<CreditDto> reportBetween(
            @RequestParam("dateInit")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateInit,
            @RequestParam("dateEnd")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateEnd,
            @RequestParam("productName") String productName) {
        return this.creditService.reportBetween(dateInit,dateEnd,productName);
    }

    @GetMapping("/report-between-date")
    public Flux<CreditDto> reportBetweenInterval(@RequestBody ParamReport paramReport) {
        return this.creditService.reportBetweenInterval(paramReport);
    }


}
