import useGetSocketUrl from '@hooks/useGetSocketUrl';
import useHover from '@hooks/useHover';
import useTeleportToken from '@hooks/useTeleportToken';
import { usePlayerIdValue } from '@store/index';
import {
  useGameInfoValue,
  usePlayersValue,
  useResetTeleportStatus,
  useSetIsArrivedTrue,
} from '@store/reducer';
import { PlayerStatusType } from '@store/reducer/type';
import { useCallback, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import useWebSocket from 'react-use-websocket';
import { styled } from 'styled-components';
import Dice from './Dice';
import Roulette from './Roulette';

type CenterAreaProps = {
  currentStatus: PlayerStatusType;
  targetLocation: number | null;
  resetTargetLocation: () => void;
};

export default function CenterArea({
  currentStatus,
  targetLocation,
  resetTargetLocation,
}: CenterAreaProps) {
  const {
    handleMouseEnter: handleBailBtnEnter,
    handleMouseLeave: handleBailBtnLeave,
    isHover: isBailBtnHover,
  } = useHover();
  const {
    handleMouseEnter: handleEscapeBtnEnter,
    handleMouseLeave: handleEscapeBtnLeave,
    isHover: isEscapeBtnHover,
  } = useHover();
  const { gameId } = useParams();
  const playerId = usePlayerIdValue();
  const players = usePlayersValue();
  const {
    currentPlayerId,
    firstPlayerId,
    isMoveFinished,
    teleportPlayerId,
    teleportLocation,
  } = useGameInfoValue();
  const teleportToken = useTeleportToken();
  const setIsArrivedTrue = useSetIsArrivedTrue();
  const resetTeleportStatus = useResetTeleportStatus();
  const socketUrl = useGetSocketUrl();
  const { sendJsonMessage } = useWebSocket(socketUrl, {
    share: true,
  });

  const isMyTurn = playerId === currentPlayerId;
  const eventTime = currentPlayerId === null;
  const isPrison = currentStatus === 'prison';
  const isTeleport = currentStatus === 'teleport';
  const isFirstPlayer = firstPlayerId === playerId;

  const defaultStart =
    isMyTurn && !eventTime && !isPrison && !isTeleport && !isMoveFinished;
  const prisonStart = isMyTurn && !eventTime && isPrison && !isMoveFinished;
  const teleportStart = isMyTurn && !eventTime && isTeleport && !isMoveFinished;

  useEffect(() => {
    if (!eventTime) return;
    if (firstPlayerId !== playerId) return;
    const message = {
      type: 'events',
      gameId,
    };
    sendJsonMessage(message);
  }, [eventTime, gameId, playerId, firstPlayerId, sendJsonMessage]);

  const sendCellMessage = useCallback(
    (playerId: string) => {
      const message = {
        type: 'cell',
        gameId,
        playerId,
      };
      sendJsonMessage(message);
    },
    [gameId, sendJsonMessage]
  );

  useEffect(() => {
    if (!teleportLocation) return;
    const targetPlayer = players.find(
      (player) => player.playerId === teleportPlayerId
    );
    if (!targetPlayer) return;
    teleportToken({
      location: teleportLocation,
      playerData: targetPlayer,
    });
    if (playerId === teleportPlayerId) {
      sendCellMessage(teleportPlayerId);
    }
    setIsArrivedTrue();
    resetTargetLocation();
    resetTeleportStatus();
  }, [
    players,
    playerId,
    teleportPlayerId,
    teleportLocation,
    teleportToken,
    sendCellMessage,
    setIsArrivedTrue,
    resetTargetLocation,
    resetTeleportStatus,
  ]);

  const throwDice = () => {
    const message = {
      type: 'dice',
      gameId,
      playerId,
    };
    sendJsonMessage(message);
  };

  const endTurn = () => {
    const message = {
      type: 'endTurn',
      gameId,
      playerId,
    };
    sendJsonMessage(message);
  };

  const handleBail = () => {
    const message = {
      type: 'bail',
      gameId,
      playerId,
    };
    sendJsonMessage(message);
  };

  const handleEscape = () => {
    const message = {
      type: 'prisonDice',
      gameId,
      playerId,
    };
    sendJsonMessage(message);
  };

  const handleTeleport = () => {
    if (!targetLocation) {
      alert('이동할 칸을 선택해주세요.');
      return;
    }
    const message = {
      type: 'teleport',
      gameId,
      playerId,
      location: targetLocation,
    };
    sendJsonMessage(message);
  };

  const sendStatusBoardMessage = () => {
    const message = {
      type: 'statusBoard',
      gameId,
    };

    if (isFirstPlayer) {
      sendJsonMessage(message);
    }
  };

  return (
    <Center>
      {eventTime && (
        <Roulette sendStatusBoardMessage={sendStatusBoardMessage} />
      )}
      {!eventTime && <Dice sendCellMessage={sendCellMessage} />}
      {defaultStart && (
        <Button onClick={throwDice} disabled={isMoveFinished}>
          굴리기
        </Button>
      )}
      {prisonStart && (
        <Wrapper>
          <Button
            onMouseEnter={handleBailBtnEnter}
            onMouseLeave={handleBailBtnLeave}
            onClick={handleBail}
          >
            {isBailBtnHover ? '-5,000,000₩' : '보석금 지불'}
          </Button>
          <Button
            onMouseEnter={handleEscapeBtnEnter}
            onMouseLeave={handleEscapeBtnLeave}
            onClick={handleEscape}
          >
            {isEscapeBtnHover ? '주사위 더블시 탈출' : '굴려서 탈출'}
          </Button>
        </Wrapper>
      )}
      {teleportStart && (
        <>
          <div>이동할 칸을 선택한 후 이동하기 버튼을 눌러주세요.</div>
          <Button onClick={handleTeleport}>이동하기</Button>
        </>
      )}
      {isMyTurn && isMoveFinished && <Button onClick={endTurn}>턴종료</Button>}
    </Center>
  );
}

const Center = styled.div`
  width: 30rem;
  height: 30rem;
  position: absolute;
  top: 6rem;
  left: 6rem;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
`;

const Wrapper = styled.div`
  display: flex;
  gap: 1rem;
`;

const Button = styled.button`
  width: 8rem;
  height: 4rem;
  padding: 0.5rem;
  border-radius: ${({ theme: { radius } }) => radius.small};
  color: ${({ theme: { color } }) => color.neutralText};
  background-color: ${({ theme: { color } }) => color.neutralBackground};

  &:disabled {
    opacity: 0.6;
  }
`;
