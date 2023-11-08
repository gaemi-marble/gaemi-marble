import EventModal from '@components/Modal/EventModal/EventModal';
import useSound from '@hooks/useSound';
import {
  useGameInfoValue,
  useResetEventRound,
  useRouletteTimer,
} from '@store/reducer';
import { delay } from '@utils/index';
import { useEffect, useState } from 'react';
import { Wheel } from 'react-custom-roulette';
import { styled } from 'styled-components';

export default function Roulette() {
  const [mustSpin, setMustSpin] = useState(false);
  const [prizeNumber, setPrizeNumber] = useState(0);
  const [isEventModalOpen, setIsEventModalOpen] = useState(false);

  const [rouletteTimer, setRouletteTimer] = useRouletteTimer();
  const { eventList, eventResult } = useGameInfoValue();
  const resetGameInfo = useResetEventRound();

  const [isRolling, setIsRolling] = useState(false);
  const { sound: RouletteRollingSound } = useSound({
    src: '/sound/roulette.mp3',
  });

  useEffect(() => {
    let isMounted = true;
    const stockSellTimer = async () => {
      await delay(1000);
      if (!isMounted) return;
      if (rouletteTimer === undefined) {
        alert('이벤트 룰렛을 정상적으로 불러오지 못했습니다.');
        return;
      }
      if (rouletteTimer >= 0 && !isRolling) {
        setRouletteTimer((prev) => prev - 1);
      }
    };
    stockSellTimer();

    return () => {
      isMounted = false;
    };
  }, [rouletteTimer, setRouletteTimer, isRolling]);

  useEffect(() => {
    if (eventResult === '') return;
    const prizeNumber = eventList.findIndex(
      (event) => event.title === eventResult
    );
    setPrizeNumber(prizeNumber);
    setIsRolling(true);
    setMustSpin(true);
  }, [eventResult, eventList]);

  if (eventList.length === 0) return null;
  const wheelData = eventList.map((event) => {
    return { option: event.title };
  });

  const handleSpinDone = async () => {
    setIsEventModalOpen(true);
    setIsRolling(false);
    await delay(5000);
    resetGameInfo();
    setMustSpin(false);
    setIsEventModalOpen(false);
  };

  return (
    <>
      <Wheel
        mustStartSpinning={mustSpin}
        prizeNumber={prizeNumber}
        data={wheelData}
        fontSize={16}
        spinDuration={0.5}
        radiusLineWidth={2}
        outerBorderWidth={2}
        pointerProps={{ style: { width: '70px', height: '70px' } }}
        textColors={['#FCF5ED', '#000']}
        backgroundColors={['#3e3e3e', '#f4acb7']}
        onStopSpinning={handleSpinDone}
      />
      <Wrapper>
        <Timer>남은 매도시간: {rouletteTimer}</Timer>
      </Wrapper>
      {isEventModalOpen && <EventModal />}
      {isRolling && RouletteRollingSound}
    </>
  );
}

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;

const Timer = styled.div`
  font-size: ${({ theme }) => theme.fontSize.sMedium};
`;
