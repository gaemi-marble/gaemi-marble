import { styled } from 'styled-components';

export default function SignUpPage() {
  return (
    <Container>
      <Title>Gaemi Marble</Title>
      <Wrapper>
        <Input>
          <span>아이디</span>
          <input aria-label="아이디" type="text" name="playerId" />
        </Input>
        <Input>
          <span>비밀번호</span>
          <input aria-label="비밀번호" type="text" name="playerPW" />
        </Input>
      </Wrapper>
      <Button>가입하기</Button>
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

const Title = styled.h1`
  font-size: ${({ theme: { fontSize } }) => fontSize.xLarge};
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 16px;
`;

const Input = styled.label`
  span {
    margin-right: 8px;
  }

  input {
    color: black;
  }
`;

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
