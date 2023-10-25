package codesquad.gaemimarble.game.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import codesquad.gaemimarble.game.dto.response.generalStatusBoard.GameStockStatusResponse;
import codesquad.gaemimarble.game.dto.response.userStatusBoard.GameUserStatusBoardResponse;
import codesquad.gaemimarble.game.dto.response.userStatusBoard.StockNameResponse;
import codesquad.gaemimarble.game.entity.Player;
import codesquad.gaemimarble.game.entity.Stock;

@Mapper
public interface GameMapper {
	GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

	@Mapping(target = "quantity", source = "stock.remainingStock")
	@Mapping(target = "price", source = "stock.currentPrice")
	@Mapping(target = "theme", source = "themeName")
	GameStockStatusResponse toGameStockStatusResponse(Stock stock, String themeName);

	@Mapping(target = "stockList", source = "stockList")
	GameUserStatusBoardResponse toGameUserStatusBoardResponse(Player player, List<StockNameResponse> stockList);

	StockNameResponse toStockNameResponse(Stock stock);
}
