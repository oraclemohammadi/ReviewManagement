package com.milo.amz.review.service.mapper;

import com.milo.amz.review.domain.*;
import com.milo.amz.review.service.dto.MarketPlaceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MarketPlace and its DTO MarketPlaceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MarketPlaceMapper {

    MarketPlaceDTO marketPlaceToMarketPlaceDTO(MarketPlace marketPlace);

    List<MarketPlaceDTO> marketPlacesToMarketPlaceDTOs(List<MarketPlace> marketPlaces);

    MarketPlace marketPlaceDTOToMarketPlace(MarketPlaceDTO marketPlaceDTO);

    List<MarketPlace> marketPlaceDTOsToMarketPlaces(List<MarketPlaceDTO> marketPlaceDTOs);
}
