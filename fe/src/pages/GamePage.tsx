import { styled } from 'styled-components';

export default function GamePage() {
  return (
    <Container>
      <Header>
        <Logo>Gaemi Marble</Logo>
        <Button>나가기</Button>
      </Header>
      <Main>
        <PlayerList />
        <GameBoard />
      </Main>
    </Container>
  );
}

const Container = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 16px;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;

const Header = styled.div`
  width: 100%;
  height: 8rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
  font-size: ${({ theme: { fontSize } }) => fontSize.xLarge};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;

const Logo = styled.h1`
  display: block;
  color: ${({ theme: { color } }) => color.accentText};
`;

const Main = styled.div`
  height: 100%;
`;

const Button = styled.button`
  width: 200px;
  height: 50px;
  border: 1px solid;
  border-color: ${({ theme: { color } }) => color.accentText};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  font-size: ${({ theme: { fontSize } }) => fontSize.medium};
  cursor: pointer;
`;

const PlayerList = styled.div``;

const GameBoard = styled.div`
  width: 500px;
  height: 500px;
  border: 1px solid;
  border-color: ${({ theme: { color } }) => color.accentText};
`;
