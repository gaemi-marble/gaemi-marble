import { postCreateRoom } from '@api/index';
import HomeHeader from '@components/Header/HomeHeader';
import EnterModal from '@components/Modal/EnterModal/EnterModal';
import { Icon } from '@components/icon/Icon';
import { ROUTE_PATH } from '@router/constants';
import { useSetGame } from '@store/reducer';
import {
  INITIAL_GAME,
  INITIAL_PLAYER,
  INITIAL_STOCK,
} from '@store/reducer/constants';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

export default function HomePage() {
  const navigate = useNavigate();
  const [isEnterModalOpen, setIsEnterModalOpen] = useState(false);
  const setGame = useSetGame();

  useEffect(() => {
    setGame({
      game: INITIAL_GAME,
      players: INITIAL_PLAYER,
      stocks: INITIAL_STOCK,
    });
  }, [setGame]);

  const handleOpenModal = () => {
    // Memo: 현재 버전 - 모달 띄워서 방 번호 입력
    // Todo: 버전업 - 방 목록 보여주기
    setIsEnterModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsEnterModalOpen(false);
  };

  const handleCreateRoom = async () => {
    const res = await postCreateRoom();

    if (res.status === 201) {
      navigate(`${ROUTE_PATH.GAME}/${res.data.gameId}`);
    }
  };

  return (
    <>
      <HomeHeader />
      <Main>
        <Button onClick={handleCreateRoom}>
          방 만들기
          <Icon name="plus" size="3rem" />
        </Button>
        <Button onClick={handleOpenModal}>입장하기</Button>
      </Main>
      {isEnterModalOpen && <EnterModal handleClose={handleCloseModal} />}
    </>
  );
}

const Main = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  gap: 8rem;
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
