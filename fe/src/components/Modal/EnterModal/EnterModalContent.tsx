import { getGameCheck } from '@api/index';
import { ROUTE_PATH } from '@router/constants';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';

export default function EnterModalContent() {
  const navigate = useNavigate();
  const [roomNumber, setRoomNumber] = useState('');

  const handleChangeRoomNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRoomNumber(e.target.value);
  };

  const handleEnterRoom = async () => {
    // Todo: 유효한 방인지 검증하는 api 완성되면 추가
    const gameId = +roomNumber;
    const res = await getGameCheck(gameId);
    const { isPresent, isFull } = res.data;

    if (!(res.status === 200)) {
      // Todo: 방 입장 실패 시 토스트 띄우기
      alert('서버오류로 입장할 수 없습니다');
      return;
    }

    if (!isPresent) {
      alert('존재하지 않는 게임 방 입니다');
      return;
    }

    if (isFull) {
      alert('게임 방이 가득 찼습니다');
      return;
    }

    navigate(`${ROUTE_PATH.GAME}/${roomNumber}`);
  };

  return (
    <Content>
      <Input value={roomNumber} onChange={handleChangeRoomNumber} />
      <Button onClick={handleEnterRoom}>입장하기</Button>
    </Content>
  );
}

const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
`;

const Input = styled.input`
  padding: 0 0.5rem;
  border-radius: ${({ theme: { radius } }) => radius.small};
`;

const Button = styled.button`
  width: 10rem;
  height: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border: ${({ theme: { color } }) => `1px solid ${color.neutralTextStrong}`};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  font-size: ${({ theme: { fontSize } }) => fontSize.small};
  cursor: pointer;

  &:hover {
    border-color: ${({ theme: { color } }) => color.accentPrimary};
    color: ${({ theme: { color } }) => color.accentText};
    background-color: ${({ theme: { color } }) => color.accentBackground};
  }
`;
