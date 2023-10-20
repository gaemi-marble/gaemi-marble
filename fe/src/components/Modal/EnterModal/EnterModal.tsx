import useOutsideClick from '@hooks/useOutsideClick';
import Modal from '../Modal';
import EnterModalContent from './EnterModalContent';

type EnterModalProps = {
  onClose: () => void;
};

export default function EnterModal({ onClose }: EnterModalProps) {
  const { ref: enterModalRef } = useOutsideClick<HTMLDivElement>(onClose);

  return (
    <Modal
      ref={enterModalRef}
      header="방 번호를 입력하세요"
      content={<EnterModalContent />}
    />
  );
}
