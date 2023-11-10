import { Icon } from '@components/icon/Icon';
import { ANT_LIST } from '@pages/constants';
import { useGameInfoValue, useStocksValue } from '@store/reducer';
import { PlayerType } from '@store/reducer/type';
import { addCommasToNumber } from '@utils/index';
import { styled } from 'styled-components';

type PlayerInfoProps = {
  player: PlayerType;
};

export default function PlayerInfo({ player }: PlayerInfoProps) {
  const antName = ANT_LIST.find((ant) => ant.order === player?.order)!.antName;
  const { currentPlayerId } = useGameInfoValue();
  const stocks = useStocksValue();
  const { userStatusBoard, playerId } = player;
  const isCurrentPlayer = currentPlayerId === playerId;

  const playerStocks = userStatusBoard.stockList;
  const stockAsset = playerStocks.reduce((acc, playerStock) => {
    const stock = stocks.find(
      (stockItem) => stockItem.name === playerStock.name
    );
    if (stock) {
      const stockValue = stock.price * playerStock.quantity;
      return acc + stockValue;
    }
    return acc;
  }, 0);

  return (
    <UserInfo $isCurrentPlayer={isCurrentPlayer}>
      <IconContainer>
        <Icon name={antName} size="8rem" />
      </IconContainer>
      <PlayerInfoContainer>
        <PlayerId>{playerId}</PlayerId>
        <PlayerProperty>
          <PropertyText>
            보유 현금: {addCommasToNumber(userStatusBoard.cashAsset)}원
          </PropertyText>
          <PropertyText>
            주식 가치: {addCommasToNumber(stockAsset)}원
          </PropertyText>
          <PropertyText>
            총 자산:
            {addCommasToNumber(userStatusBoard.cashAsset + stockAsset)}원
          </PropertyText>
        </PlayerProperty>
      </PlayerInfoContainer>
    </UserInfo>
  );
}

const UserInfo = styled.div<{ $isCurrentPlayer: boolean }>`
  display: flex;
  gap: 0.5rem;
  padding: 0.5rem;
  border: ${({ $isCurrentPlayer, theme: { color } }) =>
    $isCurrentPlayer ? `3px solid ${color.systemWarning}` : 'none'};
  border-radius: ${({ theme: { radius } }) => radius.small};
  color: ${({ theme: { color } }) => color.neutralText};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
`;

const IconContainer = styled.div`
  width: 8rem;
  height: 10rem;
  display: flex;
  align-items: center;
  border-radius: ${({ theme: { radius } }) => radius.small};
  background-color: ${({ theme: { color } }) => color.systemBackground};
`;

const PlayerInfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const PlayerId = styled.span`
  font-size: ${({ theme: { fontSize } }) => fontSize.sMedium};
`;

const PlayerProperty = styled.div`
  width: 12.5rem;
  height: 7rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0.5rem 0 0.5rem 0.2rem;
  border-radius: ${({ theme: { radius } }) => radius.small};
  font-size: ${({ theme: { fontSize } }) => fontSize.xSmall};
  color: ${({ theme: { color } }) => color.neutralText};
  background-color: ${({ theme: { color } }) => color.neutralBackgroundBold};
`;

const PropertyText = styled.span`
  display: block;
`;
