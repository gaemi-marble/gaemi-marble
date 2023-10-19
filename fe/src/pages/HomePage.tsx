import EnterModal from '@components/Modal/EnterModal/EnterModal';
import { Icon } from '@components/icon/Icon';
import { useState } from 'react';
import { styled } from 'styled-components';

export default function HomePage() {
  const [isEnterModalOpen, setIsEnterModalOpen] = useState(false);

  const onCloseModal = () => {
    setIsEnterModalOpen(false);
  };

  const onCreateRoom = () => {
    console.log('방 생성');
  };

  const onEnterRoom = () => {
    // Memo: 현재 버전 - 모달 띄워서 방 번호 입력
    // Todo: 버전업 - 방 목록 보여주기
    setIsEnterModalOpen(true);
  };

  return (
    <>
      <Main>
        <Button onClick={onCreateRoom}>
          방 만들기
          <Icon name="plus" size="3rem" />
        </Button>
        <Button onClick={onEnterRoom}>입장하기</Button>
      </Main>
      {isEnterModalOpen && <EnterModal onClose={onCloseModal} />}
    </>
  );
}

const Main = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  gap: 8rem;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;

const Button = styled.button`
  width: 16rem;
  height: 10rem;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 12rem;
  border: ${({ theme: { color } }) => `1px solid ${color.accentBorder}`};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  font-size: ${({ theme: { fontSize } }) => fontSize.medium};
  cursor: pointer;

  &:hover {
    border-color: ${({ theme: { color } }) => color.neutralBorderStrong};
    color: ${({ theme: { color } }) => color.neutralTextStrong};
    background-color: ${({ theme: { color } }) => color.neutralBackground};

    svg path {
      stroke: ${({ theme: { color } }) => color.neutralTextStrong};
    }
  }
`;
