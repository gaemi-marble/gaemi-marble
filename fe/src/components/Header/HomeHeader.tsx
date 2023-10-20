import { postLogout } from '@api/index';
import useHover from '@hooks/useHover';
import { ROUTE_PATH } from '@router/constants';
import {
  useSetAccessToken,
  useSetPlayer,
  useSetRefreshToken,
} from '@store/index';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { Icon } from '../icon/Icon';

// Memo: 홈 화면에만 적용시켜야 할듯?
export default function HomeHeader() {
  const navigate = useNavigate();
  const { hoverRef, isHover } = useHover<HTMLDivElement>();
  const setPlayer = useSetPlayer();
  const setAccessToken = useSetAccessToken();
  const setRefreshToken = useSetRefreshToken();

  const handleLogout = async () => {
    const res = await postLogout();
    if (res.status === 200) {
      setPlayer('');
      setAccessToken('');
      setRefreshToken('');
      localStorage.removeItem('playerId');
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');

      navigate(ROUTE_PATH.SIGNIN);
    }
  };

  return (
    <StyledHeader>
      <Logo>Gaemi Marble</Logo>
      <User ref={hoverRef}>
        {isHover ? (
          <Icon name="exit" size="3rem" onClick={handleLogout} />
        ) : (
          <Icon name="sample" size="3rem" />
        )}
      </User>
    </StyledHeader>
  );
}

const StyledHeader = styled.div`
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
  cursor: pointer;
`;

const User = styled.div`
  width: 3rem;
  height: 3rem;
  border-radius: ${({ theme: { radius } }) => radius.half};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
`;
