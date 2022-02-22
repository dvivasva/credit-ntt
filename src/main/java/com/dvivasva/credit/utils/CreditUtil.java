package com.dvivasva.credit.utils;

import com.dvivasva.credit.dto.CreditDto;
import com.dvivasva.credit.entity.Credit;
import org.springframework.beans.BeanUtils;

public class CreditUtil {

    public static CreditDto entityToDto(Credit credit){
        var creditDto=new CreditDto();
        BeanUtils.copyProperties(credit,creditDto);
        return creditDto;
    }
    public static Credit dtoToEntity(CreditDto creditDto){
        var entity=new Credit();
        BeanUtils.copyProperties(creditDto,entity);
        return entity;
    }

}
