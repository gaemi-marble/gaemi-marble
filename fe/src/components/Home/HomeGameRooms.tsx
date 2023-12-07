import { getGameRooms } from '@api/index';
import { gameRoomsAtom } from '@store/index';
import { useAtom } from 'jotai';
import { useCallback, useEffect } from 'react';
import { styled } from 'styled-components';
import HomeGameRoom from './HomeGameRoom';

export default function HomeGameRooms() {
  const [gameRooms, setGameRooms] = useAtom(gameRoomsAtom);

  const fetchGameRooms = useCallback(async () => {
    const res = await getGameRooms();
    const waitingRoomList = res.data;
    setGameRooms(waitingRoomList);
  }, [setGameRooms]);

  useEffect(() => {
    fetchGameRooms();

    const fetchGameRoomsInterval = setInterval(fetchGameRooms, 10000);

    return () => clearInterval(fetchGameRoomsInterval);
  }, [fetchGameRooms]);

  return (
    <GameRooms>
      {gameRooms.map((room) => (
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
