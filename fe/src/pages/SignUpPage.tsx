import { FormEvent, useState } from 'react';
import { styled } from 'styled-components';
import { signup } from '../api';

export default function SignUpPage() {
  const [playerId, setPlayerId] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();

    const res = await signup(playerId, password);
    return res.data;
  };

  return (
    <Container>
      <Title>Gaemi Marble</Title>
      <SignUpForm onSubmit={handleSubmit}>
        <Input>
          <span>아이디</span>
          <input
            aria-label="아이디"
            type="text"
            name="playerId"
            value={playerId}
            onChange={(event) => {
              setPlayerId(event.target.value);
            }}
          />
        </Input>
        <Input>
          <span>비밀번호</span>
          <input
            aria-label="비밀번호"
            type="password"
            name="password"
            value={password}
            onChange={(event) => {
              setPassword(event.target.value);
            }}
          />
        </Input>
        <Button type="submit">가입하기</Button>
      </SignUpForm>
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

const Title = styled.h1`
  font-size: ${({ theme: { fontSize } }) => fontSize.xLarge};
`;

const SignUpForm = styled.form`
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
