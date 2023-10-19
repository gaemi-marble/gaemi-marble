import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';

export default function Layout() {
  return (
    <Page>
      <Outlet />
    </Page>
  );
}

const Page = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;
