import GameBoard from '@components/GameBoard/GameBoard';
import GameHeader from '@components/Header/GameHeader';
import GameOverModal from '@components/Modal/GameOverModal/GameOverModal';
import GoldCardModal from '@components/Modal/GoldCardModal/GoldCardModal';
import StockBuyModal from '@components/Modal/StockBuyModal/StockBuyModal';
import LeftPlayers from '@components/Player/LeftPlayers';
import RightPlayers from '@components/Player/RightPlayers';
import useGetSocketUrl from '@hooks/useGetSocketUrl';
import useWindowSize from '@hooks/useWindowSize';
import { usePlayerIdValue } from '@store/index';
import { useGameInfoValue, usePlayersValue } from '@store/reducer';
import useGameReducer from '@store/reducer/useGameReducer';
import { useEffect } from 'react';
import Confetti from 'react-confetti';
import useWebSocket from 'react-use-websocket';
import { styled } from 'styled-components';
import { GOLD_CARD_LOCATIONS, STOCK_LOCATION } from './constants';

export default function GamePage() {
  const playersInfo = usePlayersValue();
  const playerId = usePlayerIdValue();
  const {
    isMoveFinished,
    currentPlayerId,
    goldCardInfo,
    isArrived,
    isPlaying,
    ranking,
  } = useGameInfoValue();
  const { dispatch } = useGameReducer();
  const socketUrl = useGetSocketUrl();
  const { width, height } = useWindowSize();

  const { lastMessage } = useWebSocket(socketUrl, {
    share: true,
  });

  // Memo: dependency에 dispatch 추가시 무한렌더링
  useEffect(() => {
    if (lastMessage !== null) {
      const messageFromServer = JSON.parse(lastMessage?.data);
      dispatch({
        type: messageFromServer.type,
        payload: messageFromServer.data,
      });
    }
  }, [lastMessage]);

  const isCurrentPlayer = currentPlayerId === playerId;
  const currentLocation = playersInfo.find(
    (player) => player.playerId === playerId
  )?.location;
  const isLocatedGoldCard = GOLD_CARD_LOCATIONS.includes(currentLocation ?? 0);
  const isLocatedStockCell = STOCK_LOCATION.includes(currentLocation ?? 0);
  const isGameOver = !isPlaying && ranking.length !== 0;

  return (
    <>
      <Container>
        <GameHeader />
        <Main>
          <LeftPlayers />
          <GameBoard />
          <RightPlayers />
        </Main>
      </Container>
      {isLocatedGoldCard &&
        isMoveFinished &&
        isCurrentPlayer &&
        isArrived &&
        goldCardInfo.title && <GoldCardModal />}
      {isLocatedStockCell && isMoveFinished && isCurrentPlayer && isArrived && (
        <StockBuyModal />
      )}
      {isGameOver && <GameOverModal />}
      {isGameOver && <Confetti width={width} height={height} />}
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 1;
  color: ${({ theme: { color } }) => color.accentText};
`;

const Main = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  flex: 1;
  padding: 0 1rem;
`;
