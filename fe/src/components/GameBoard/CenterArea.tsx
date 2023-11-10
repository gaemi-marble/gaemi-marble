import useGetSocketUrl from '@hooks/useGetSocketUrl';
import useHover from '@hooks/useHover';
import useMoveToken from '@hooks/useMoveToken';
import { usePlayerIdValue } from '@store/index';
import {
  useGameInfoValue,
  usePlayersValue,
  useResetTeleportLocation,
  useSetGameInfo,
} from '@store/reducer';
import { PlayerStatusType } from '@store/reducer/type';
import { useEffect } from 'react';
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
  const { hoverRef: bailRef, isHover: isBailBtnHover } =
    useHover<HTMLButtonElement>();
  const { hoverRef: escapeRef, isHover: isEscapeBtnHover } =
    useHover<HTMLButtonElement>();
  const { gameId } = useParams();
  const playerId = usePlayerIdValue();
  const players = usePlayersValue();
  const { currentPlayerId, firstPlayerId, isMoveFinished, teleportLocation } =
    useGameInfoValue();
  const setGameInfo = useSetGameInfo();
  const moveToken = useMoveToken();
  const resetTeleportLocation = useResetTeleportLocation();
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

  const currentPlayerInfo = players.find(
    (player) => player.playerId === currentPlayerId
  );

  useEffect(() => {
    if (!eventTime) return;
    if (firstPlayerId !== playerId) return;
    const message = {
      type: 'events',
      gameId,
    };
    sendJsonMessage(message);
  }, [eventTime, gameId, playerId, firstPlayerId, sendJsonMessage]);

  const teleportToken = async () => {
    if (!currentPlayerInfo || !teleportLocation) return;
    const cellCount = calculateCellCount({
      targetCell: teleportLocation,
      currentCell: currentPlayerInfo.gameBoard.location,
    });

    await moveToken({
      diceCount: cellCount,
      playerGameBoardData: currentPlayerInfo.gameBoard,
      type: 'teleport',
    });

    if (isMyTurn) {
      sendCellMessage();
    }

    setGameInfo((prev) => {
      return {
        ...prev,
        isArrived: true,
      };
    });
    resetTargetLocation();
    resetTeleportLocation();
  };

  useEffect(() => {
    if (!teleportLocation) return;
    teleportToken();
  }, [teleportLocation]);

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

  const sendCellMessage = () => {
    const message = {
      type: 'cell',
      gameId,
      playerId,
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

  const calculateCellCount = ({
    targetCell,
    currentCell,
  }: {
    targetCell: number;
    currentCell: number;
  }) => {
    const cellCount = (24 + targetCell - currentCell) % 24;
    return cellCount;
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
          <Button ref={bailRef} onClick={handleBail}>
            {/* Memo: 호버시 내부 텍스트가 안 바뀌는 버그 발견 */}
            {isBailBtnHover ? '-5,000,000₩' : '보석금 지불'}
          </Button>
          <Button ref={escapeRef} onClick={handleEscape}>
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
