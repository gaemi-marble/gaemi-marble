import useOutsideClick from '@hooks/useOutsideClick';
import Modal from '../Modal';
import EnterModalContent from './EnterModalContent';

export default function EnterModal({ onClose }: { onClose: () => void }) {
  const { ref: productStatusModalRef } =
    useOutsideClick<HTMLDivElement>(onClose);

  return (
    <Modal
      ref={productStatusModalRef}
      header="방 번호를 입력하세요"
      content={<EnterModalContent />}
    />
  );
}
