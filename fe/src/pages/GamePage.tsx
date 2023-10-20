import GameBoard from '@components/GameBoard/GameBoard';
import { styled } from 'styled-components';

export default function GamePage() {
  return (
    <Container>
      <Main>
        <GameBoard />
      </Main>
    </Container>
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
  height: 100%;
`;
