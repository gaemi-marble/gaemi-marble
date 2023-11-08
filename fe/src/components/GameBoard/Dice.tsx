import useMoveToken from '@hooks/useMoveToken';
import useSound from '@hooks/useSound';
import { useGameInfoValue, usePlayers, useSetGameInfo } from '@store/reducer';
import { useEffect, useRef, useState } from 'react';
import ReactDice, { ReactDiceRef } from 'react-dice-complete';
import { styled } from 'styled-components';

export default function Dice() {
  const [diceValue, setDiceValue] = useState(0);
  const reactDice = useRef<ReactDiceRef>(null);
  const [players] = usePlayers();
  const gameInfo = useGameInfoValue();
  const moveToken = useMoveToken();
  const setGameInfo = useSetGameInfo();
  const [isRolling, setIsRolling] = useState(false);
  const { sound: DiceRollSound } = useSound({
    src: '/sound/roll.mp3',
  });
  const [dice1, dice2] = gameInfo.dice;

  useEffect(() => {
    if (dice1 === 0 || dice2 === 0) return;
    rollDice(dice1, dice2);
  }, [gameInfo.dice]);

  const rollDice = (dice1: number, dice2: number) => {
    reactDice.current?.rollAll([dice1, dice2]);
    setIsRolling(true);
  };

  const rollDone = async () => {
    if (dice1 === 0 || dice2 === 0) return;

    const totalDiceValue = dice1 + dice2;
    setDiceValue(totalDiceValue);
    const targetPlayer = players.find(
      (player) => player.playerId === gameInfo.currentPlayerId
    );
    setIsRolling(false);

    if (!targetPlayer) return;
    if (!targetPlayer.gameboard.hasEscaped) return;

    await moveToken({
      diceCount: totalDiceValue,
      playerGameBoardData: targetPlayer.gameboard,
    });

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
