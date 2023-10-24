package codesquad.gaemimarble.game.entity;

import java.util.ArrayList;
import java.util.List;

import codesquad.gaemimarble.game.entity.ground.Cell;
import codesquad.gaemimarble.game.entity.ground.Company;
import codesquad.gaemimarble.game.entity.ground.GoldCard;
import codesquad.gaemimarble.game.entity.ground.GoodNews;
import codesquad.gaemimarble.game.entity.ground.Prison;
import codesquad.gaemimarble.game.entity.ground.Start;
import codesquad.gaemimarble.game.entity.ground.Tax;
import codesquad.gaemimarble.game.entity.ground.Teleport;

public class Board {
	private final List<Cell> board;

	public Board() {
		board = new ArrayList<>();
		Start start = Start.builder().build();
		Company company = Company.builder().build();
		GoldCard goldCard = GoldCard.builder().build();
		Tax tax = Tax.builder().build();
		Teleport teleport = Teleport.builder().build();
		Prison prison = Prison.builder().build();
		GoodNews goodNews = GoodNews.builder().build();
		board.add(start);
		board.add(company);
		board.add(company);
		board.add(company);
		board.add(company);
		board.add(company);
		board.add(prison);
		board.add(company);
		board.add(company);
		board.add(goldCard);
		board.add(company);
		board.add(company);
		board.add(goodNews);
		board.add(company);
		board.add(company);
		board.add(tax);
		board.add(company);
		board.add(company);
		board.add(teleport);
		board.add(company);
		board.add(company);
		board.add(goldCard);
		board.add(company);
		board.add(company);
	}
}
