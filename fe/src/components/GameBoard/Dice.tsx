import useMoveToken from '@hooks/useMoveToken';
import useSound from '@hooks/useSound';
import { useGameInfoValue, usePlayers, useSetGameInfo } from '@store/reducer';
import { useEffect, useRef, useState } from 'react';
import ReactDice, { ReactDiceRef } from 'react-dice-complete';
import { styled } from 'styled-components';

type DiceProps = {
  sendCellMessage: () => void;
};

// TODO: Dice가 반복적으로 재렌더링 되는 원인 찾아서 수정하기
export default function Dice({ sendCellMessage }: DiceProps) {
  const [diceValue, setDiceValue] = useState(0);
  const reactDice = useRef<ReactDiceRef>(null);
  const [players] = usePlayers();
  const { dice, currentPlayerId } = useGameInfoValue();
  const moveToken = useMoveToken();
  const setGameInfo = useSetGameInfo();
  const [isRolling, setIsRolling] = useState(false);
  const { sound: DiceRollSound } = useSound({
    src: '/sound/roll.mp3',
  });
  const [dice1, dice2] = dice;

  useEffect(() => {
    if (dice1 === 0 || dice2 === 0) return;
    rollDice(dice1, dice2);
  }, [dice1, dice2]);

  const rollDice = (dice1: number, dice2: number) => {
    reactDice.current?.rollAll([dice1, dice2]);
    setIsRolling(true);
  };

  const rollDone = async () => {
    if (dice1 === 0 || dice2 === 0) return;

    const totalDiceValue = dice1 + dice2;
    setDiceValue(totalDiceValue);
    const targetPlayer = players.find(
      (player) => player.playerId === currentPlayerId
    );
    setIsRolling(false);

    if (!targetPlayer) return;
    if (!targetPlayer.gameboard.hasEscaped) return;

    await moveToken({
      diceCount: totalDiceValue,
      playerGameBoardData: targetPlayer.gameboard,
    });

    sendCellMessage();

    setGameInfo((prev) => {
      return {
        ...prev,
        isArrived: true,
      };
    });
  };

  return (
    <>
      <ReactDice
        numDice={2}
        ref={reactDice}
        rollDone={rollDone}
        rollTime={1}
        faceColor="#fff"
        dotColor="#000"
        disableIndividual={true}
      />
      <DiceValue>{diceValue}</DiceValue>
      {isRolling && DiceRollSound}
    </>
  );
}

const DiceValue = styled.div`
  font-size: ${({ theme }) => theme.fontSize.medium};
`;
