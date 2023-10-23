package codesquad.gaemimarble.game.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import codesquad.gaemimarble.game.dto.response.GameStockStatusResponse;
import codesquad.gaemimarble.game.entity.Stock;

@Mapper
public interface GameMapper {
	GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

	@Mapping(target = "quantity", source = "stock.remainingStock")
	@Mapping(target = "price", source = "stock.currentPrice")
	@Mapping(target = "theme", source = "themeName")
	GameStockStatusResponse toGameStockStatusResponse(Stock stock, String themeName);
}
