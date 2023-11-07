import { PlayerRankingType } from '@store/reducer/type';
import { styled } from 'styled-components';
import PlayerRank from './PlayerRank';

type GameOverModalContentProps = {
  ranking: PlayerRankingType[];
};

export default function GameOverModalContent({
  ranking,
}: GameOverModalContentProps) {
  return (
    <>
      <span>최종 자산 순위</span>
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
      <button>나가기</button>
    </>
  );
}

const PlayerRankList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
