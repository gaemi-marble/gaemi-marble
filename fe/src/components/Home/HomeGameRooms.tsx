// import { getGameRooms } from '@api/index';
// import { gameRoomsAtom } from '@store/index';
// import { useAtom } from 'jotai';
// import { useCallback, useEffect } from 'react';
import { styled } from 'styled-components';
import HomeGameRoom from './HomeGameRoom';

export default function HomeGameRooms() {
  // const [gameRooms, setGameRooms] = useAtom(gameRoomsAtom);

  // const fetchGameRooms = useCallback(async () => {
  //   const res = await getGameRooms();
  //   const waitingRoomList = res.data;
  //   setGameRooms(waitingRoomList);
  // }, [setGameRooms]);

  // useEffect(() => {
  //   fetchGameRooms();

  //   const fetchGameRoomsInterval = setInterval(fetchGameRooms, 10000);

  //   return () => clearInterval(fetchGameRoomsInterval);
  // }, [fetchGameRooms]);

  return (
    <GameRooms>
      {mockGameRooms.map((room) => (
        <HomeGameRoom key={room.gameId} room={room} />
      ))}
    </GameRooms>
  );
}

const GameRooms = styled.ul`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`;

const mockGameRooms = [
  { gameId: 0, isPlaying: false, playerCount: 2 },
  { gameId: 1, isPlaying: false, playerCount: 4 },
  { gameId: 2, isPlaying: true, playerCount: 4 },
  { gameId: 3, isPlaying: false, playerCount: 3 },
  { gameId: 4, isPlaying: true, playerCount: 4 },
  { gameId: 5, isPlaying: false, playerCount: 1 },
];
