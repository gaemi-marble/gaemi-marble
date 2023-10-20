import GameHeader from '@components/Header/GameHeader';
import PlayerCard from '@components/Player/PlayerCard';
import PlayerTestModal from '@components/Player/PlayerTestModal';
import { usePlayersValue } from '@store/reducer/player';
import { useState } from 'react';
import { styled } from 'styled-components';
import { leftPlayerOrderList, rightPlayerOrderList } from './constants';

export default function GamePage() {
  // Memo: 테스트용 임시 모달
  const [isTestModalOpen, setIsTestModalOpen] = useState(false);
  const playersInfo = usePlayersValue();

  const handleOpenModal = () => {
    setIsTestModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsTestModalOpen(false);
  };

  const leftPlayersInfo = playersInfo.filter((player) =>
    leftPlayerOrderList.includes(player.order)
  );

  const rightPlayerInfo = playersInfo.filter((player) =>
    rightPlayerOrderList.includes(player.order)
  );

  return (
    <>
      <Container>
        <GameHeader handleClickTest={handleOpenModal} />
        <Main>
          <Players>
            {leftPlayersInfo.map((playerInfo) => (
              <PlayerCard key={playerInfo.order} player={playerInfo} />
            ))}
          </Players>
          <GameBoard />
          <Players>
            {rightPlayerInfo.map((playerInfo) => (
              <PlayerCard key={playerInfo.order} player={playerInfo} />
            ))}
          </Players>
        </Main>
      </Container>
      {isTestModalOpen && <PlayerTestModal handleClose={handleCloseModal} />}
    </>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 16px;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;

const Main = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0 1rem;
`;

const GameBoard = styled.div`
  width: 42rem;
  height: 42rem;
  border: 1px solid;
  border-color: ${({ theme: { color } }) => color.accentText};
`;

const Players = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;
