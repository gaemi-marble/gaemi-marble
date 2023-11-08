import { addCommasToNumber } from '@utils/index';
import { styled } from 'styled-components';

type PlayerRankProps = {
  rank: number;
  playerId: string;
  totalAsset: number;
};

export default function PlayerRank({
  rank,
  playerId,
  totalAsset,
}: PlayerRankProps) {
  return (
    <PlayerRankCard>
      <Rank>{rank}</Rank>
      <Wrapper>
        <Name>{playerId}</Name>
        <div>{addCommasToNumber(totalAsset)}원</div>
      </Wrapper>
    </PlayerRankCard>
  );
}

const PlayerRankCard = styled.li`
  height: 6rem;
  padding: 0 1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  border: 1px solid;
  border-radius: ${({ theme }) => theme.radius.small};
  font-size: ${({ theme }) => theme.fontSize.medium};
`;

const Wrapper = styled.div`
  flex: 1;
  display: flex;
  justify-content: space-between;
  gap: 2rem;
`;

const Rank = styled.div`
  width: 3rem;
  height: 3rem;
  border: 1px solid;
  border-radius: 50%;
  text-align: center;
`;

const Name = styled.div``;
