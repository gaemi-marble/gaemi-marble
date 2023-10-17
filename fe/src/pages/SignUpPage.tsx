import { styled } from 'styled-components';

export default function SignUpPage() {
  return (
    <Container>
      <Title>Gaemi Marble</Title>
      <IdInput />
      <PWInput />
      <Button>가입하기</Button>
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
  color: white;
  background-color: royalblue;
`;

const Title = styled.h1`
  font-size: 64px;
`;

const IdInput = styled.input``;

const PWInput = styled.input``;

const Button = styled.button`
  width: 200px;
  height: 50px;
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
