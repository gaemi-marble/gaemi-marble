import useHover from '@hooks/useHover';
import { ROUTE_PATH } from '@router/constants';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import { Icon } from './icon/Icon';

// Memo: 홈 화면에만 적용시켜야 할듯?
export default function Header() {
  const navigate = useNavigate();
  const { hoverRef, isHover } = useHover<HTMLDivElement>();

  const onLogout = () => {
    // Todo: 로그아웃 기능 추가
    navigate(ROUTE_PATH.SIGNIN);
  };

  return (
    <StyledHeader>
      <Logo>Gaemi Marble</Logo>
      <User ref={hoverRef}>
        {isHover ? (
          <Icon name="logout" size="3rem" onClick={onLogout} />
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
