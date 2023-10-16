import { styled } from 'styled-components';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();

  return (
    <Container>
      <Title>Gaemi Marble</Title>
      <Button onClick={() => navigate('/signin')}>게임시작</Button>
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
