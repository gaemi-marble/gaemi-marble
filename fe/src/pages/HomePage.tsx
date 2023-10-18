import { useSetAtom } from 'jotai';
import { RESET } from 'jotai/utils';
import { styled } from 'styled-components';
import { logout } from '../api';
import { accessTokenAtom, refreshTokenAtom } from '../store';

export default function HomePage() {
  const setAccessToken = useSetAtom(accessTokenAtom);
  const setRefreshToken = useSetAtom(refreshTokenAtom);

  const handleLogout = async () => {
    const res = await logout();
    if (res.status === 200) {
      setAccessToken(RESET);
      setRefreshToken(RESET);
    }
  };

  return (
    <Container>
      <Title onClick={() => console.log('hi')}>Gaemi Marble</Title>
      <Button>게임시작</Button>
      <Button onClick={handleLogout}>로그아웃</Button>
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
