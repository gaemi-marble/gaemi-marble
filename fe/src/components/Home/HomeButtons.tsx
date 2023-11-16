import { API_STATUS } from '@api/constants';
import { getGameRooms, postCreateRoom } from '@api/index';
import EnterModal from '@components/Modal/EnterModal/EnterModal';
import { Icon } from '@components/icon/Icon';
import { ROUTE_PATH } from '@router/constants';
import { useSetGameRooms } from '@store/index';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

export default function HomeButtons() {
  const navigate = useNavigate();
  const [isEnterModalOpen, setIsEnterModalOpen] = useState(false);
  const setGameRooms = useSetGameRooms();

  const handleCreateRoom = async () => {
    const res = await postCreateRoom();

    if (res.status === API_STATUS.CREATED) {
      const { gameId } = res.data;
      navigate(`${ROUTE_PATH.GAME}/${gameId}`);
    }
  };

  const handleRefreshRooms = async () => {
    const res = await getGameRooms();

    if (res.status === API_STATUS.SUCCESS) {
      const gameRooms = res.data;
      setGameRooms(gameRooms);
    }
  };

  const handleToggleModal = () => {
    setIsEnterModalOpen((prev) => !prev);
  };

  return (
    <>
      <Buttons>
        <Button onClick={handleRefreshRooms}>
          새로고침
          <Icon name="refresh" size="1.5rem" />
        </Button>
        <RoomButtonWrapper>
          <Button onClick={handleCreateRoom}>
            방만들기
            <Icon name="plus" size="1.5rem" />
          </Button>
          <Button className="enter" onClick={handleToggleModal}>
            입장하기
            <Icon name="enter" size="2rem" />
          </Button>
        </RoomButtonWrapper>
      </Buttons>
      {isEnterModalOpen && <EnterModal handleClose={handleToggleModal} />}
    </>
  );
}

const Buttons = styled.div`
  display: flex;
  justify-content: space-between;
`;

const RoomButtonWrapper = styled.div`
  display: flex;
  gap: 2rem;
`;

const Button = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem;
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
      fill: ${({ theme: { color } }) => color.neutralTextStrong};
    }
  }

  &.enter {
    &:hover {
      svg path {
        fill: none;
      }
    }
  }
`;
