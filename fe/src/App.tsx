import { styled } from 'styled-components';

function App() {
  return (
    <StyledApp>
      <Title>Gaemi Marble</Title>
      <Button>게임시작</Button>
    </StyledApp>
  );
}

const StyledApp = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  background-color: royalblue;
`;

const Title = styled.h1`
  font-size: 64px;
`;

const Button = styled.button`
  width: 200px;
  height: 100px;
  border: 1px solid white;
  border-radius: 10px;
  font-size: 32px;
  cursor: pointer;

  &:hover {
    color: black;
    border-color: black;
    background-color: #e5e5e5;
  }
`;

export default App;
