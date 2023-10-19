import Header from '@components/Header';
import { Outlet } from 'react-router-dom';
import { styled } from 'styled-components';

export default function Layout() {
  return (
    <Page>
      <Header />
      <Outlet />
    </Page>
  );
}

const Page = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
`;
