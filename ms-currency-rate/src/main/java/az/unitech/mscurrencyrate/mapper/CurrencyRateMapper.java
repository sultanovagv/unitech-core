package az.unitech.mscurrencyrate.mapper;

import az.unitech.mscurrencyrate.entity.CurrencyRateEntity;
import az.unitech.mscurrencyrate.model.CurrencyRateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(imports = {LocalDateTime.class}, componentModel = "spring")
public interface CurrencyRateMapper {
    @Mapping(target = "eventTime", expression = "java(LocalDateTime.now())")
    CurrencyRateEntity toCurrencyRateEntity(CurrencyRateDto dto);

}