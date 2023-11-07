import { PlayerRankingType } from '@store/reducer/type';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import PlayerRank from './PlayerRank';

type GameOverModalContentProps = {
  ranking: PlayerRankingType[];
};

export default function GameOverModalContent({
  ranking,
}: GameOverModalContentProps) {
  const navigate = useNavigate();

  return (
    <>
      <span>최종 자산 순위</span>
      {ranking.length !== 0 ? (
        <PlayerRankList>
          {ranking.map((playerRank, index) => (
            <PlayerRank
              key={index}
              rank={index + 1}
              playerId={playerRank.playerId}
              totalAsset={playerRank.totalAsset}
            />
          ))}
        </PlayerRankList>
      ) : (
        <div>순위를 불러오는 중입니다...</div>
      )}
      <button onClick={() => navigate('/')}>나가기</button>
    </>
  );
}

const PlayerRankList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
