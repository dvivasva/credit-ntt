package com.dvivasva.credit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Credit {
    @Id
    private String id;
    private String customerId;  //
    //mortgage credit || credit to companies || personal credit (by consume(not revolving) or card credit (revolving))
    private String nameProduct;
    private double creditLine;
    private double availableBalance;
    private double spending;
    private double payments;
    private Date dateStart;
    private Date dateLimit;
    private int numberDueTotal; // dues
    private int numberDuePending; //deduct numberDueTotal


}
