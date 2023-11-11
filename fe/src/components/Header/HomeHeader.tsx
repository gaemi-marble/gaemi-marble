import { postLogout } from '@api/index';
import { SOUND_PATH } from '@components/constants';
import useHover from '@hooks/useHover';
import useSound from '@hooks/useSound';
import { ROUTE_PATH } from '@router/constants';
import {
  useSetAccessToken,
  useSetPlayer,
  useSetRefreshToken,
} from '@store/index';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { Icon } from '../icon/Icon';

export default function HomeHeader() {
  const navigate = useNavigate();
  const { handleMouseEnter, handleMouseLeave, isHover } = useHover();
  const setPlayer = useSetPlayer();
  const setAccessToken = useSetAccessToken();
  const setRefreshToken = useSetRefreshToken();
  const {
    isSoundPlaying,
    togglePlayingSound,
    sound: HomeBgm,
  } = useSound({
    src: SOUND_PATH.HOME_PAGE,
  });

  const handleLogout = async () => {
    const res = await postLogout();
    if (res.status === 200) {
      setPlayer('');
      setAccessToken('');
      setRefreshToken('');

      navigate(ROUTE_PATH.SIGN_IN);
    }
  };

  return (
    <>
      <StyledHeader>
        <Logo>Gaemi Marble</Logo>
        <IconWrapper>
          <IconContainer>
            <Icon
              name={isSoundPlaying ? 'soundPlaying' : 'soundMute'}
              size="3rem"
              color="neutralText"
              onClick={togglePlayingSound}
            />
          </IconContainer>
          <IconContainer
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
          >
            {isHover ? (
              <Icon name="exit" size="3rem" onClick={handleLogout} />
            ) : (
              <Icon name="sample" size="3rem" />
            )}
          </IconContainer>
        </IconWrapper>
      </StyledHeader>
      {HomeBgm}
    </>
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

const IconWrapper = styled.div`
  display: flex;
  gap: 1rem;
`;

const IconContainer = styled.div`
  width: 3rem;
  height: 3rem;
  border-radius: ${({ theme: { radius } }) => radius.half};
  background-color: ${({ theme: { color } }) => color.neutralBackground};
  cursor: pointer;

  &:hover {
    background-color: ${({ theme: { color } }) => color.accentSecondary};

    svg path {
      fill: ${({ theme: { color } }) => color.accentText};
      stroke: ${({ theme: { color } }) => color.accentText};
    }
  }
`;
