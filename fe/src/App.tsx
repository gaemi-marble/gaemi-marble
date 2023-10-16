import { styled } from 'styled-components';

function App() {
  return (
    <StyledApp>
      <StyledDiv>Test Styled Component</StyledDiv>
    </StyledApp>
  );
}

const StyledApp = styled.div`
  width: 100vw;
  height: 100vh;
`;

const StyledDiv = styled.div`
  width: 500px;
  height: 200px;
  padding: 60px 0;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  font-size: 48px;
  background-color: royalblue;
`;

export default App;
