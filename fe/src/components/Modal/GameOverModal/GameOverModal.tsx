import { useGameInfoValue } from '@store/reducer';
import Modal from '../Modal';
import GameOverModalContent from './GameOverModalContent';

export default function GameOverModal() {
  const { ranking } = useGameInfoValue();
  return (
    <Modal
      header="게임 종료"
      content={<GameOverModalContent ranking={ranking} />}
    />
  );
}
