import HomeFooter from '@components/Footer/HomeFooter';
import HomeHeader from '@components/Header/HomeHeader';
import HomeButtons from '@components/Home/HomeButtons';
import HomeGameRooms from '@components/Home/HomeGameRooms';
import { useSetGame } from '@store/reducer';
import {
  INITIAL_GAME,
  INITIAL_PLAYER,
  INITIAL_STOCK,
} from '@store/reducer/constants';
import { useEffect } from 'react';
import { styled } from 'styled-components';

export default function HomePage() {
  const setGame = useSetGame();

  useEffect(() => {
    setGame({
      game: INITIAL_GAME,
      players: INITIAL_PLAYER,
      stocks: INITIAL_STOCK,
    });
  }, [setGame]);

  return (
    <Home>
      <HomeHeader />
      <HomeMain>
        <HomeButtons />
        <HomeGameRooms />
      </HomeMain>
      <HomeFooter />
    </Home>
  );
}

const Home = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const HomeMain = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding: 0 2rem;
  overflow-y: scroll;
`;
