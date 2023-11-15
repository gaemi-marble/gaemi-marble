import { Icon } from '@components/icon/Icon';
import useGetSocketUrl from '@hooks/useGetSocketUrl';
import { usePlayerIdValue } from '@store/index';
import useWebSocket from 'react-use-websocket';
import { styled } from 'styled-components';

type DropdownProps = {
  isActive: boolean;
};

type EmoteType = 'hi' | 'angry' | 'laugh' | 'cry' | 'celebrate' | 'clock';

export default function Dropdown({ isActive }: DropdownProps) {
  const playerId = usePlayerIdValue();
  const socketUrl = useGetSocketUrl();
  const { sendJsonMessage } = useWebSocket(socketUrl, {
    share: true,
  });

  const sendEmote = (name: EmoteType) => {
    const message = {
      type: 'emoticon',
      playerId,
      name,
    };
    sendJsonMessage(message);
  };

  return (
    <Div $isActive={isActive}>
      <Icon name="hi" size="4rem" onClick={() => sendEmote('hi')} />
      <Icon name="angry" size="4rem" onClick={() => sendEmote('angry')} />
      <Icon name="laugh" size="4rem" onClick={() => sendEmote('laugh')} />
      <Icon name="cry" size="4rem" onClick={() => sendEmote('cry')} />
      <Icon
        name="celebrate"
        size="4rem"
        onClick={() => sendEmote('celebrate')}
      />
      <Icon name="clock" size="4rem" onClick={() => sendEmote('clock')} />
    </Div>
  );
}

const Div = styled.div<{ $isActive: boolean }>`
  width: 200px;
  position: absolute;
  top: 40%;
  right: 60px;
  display: flex;
  flex-wrap: wrap;
  border: 1px solid black;
  border-radius: ${({ theme }) => theme.radius.small};
  background-color: ${({ theme }) => theme.color.neutralBackground};
  opacity: ${({ $isActive }) => ($isActive ? 1 : 0)};
  visibility: ${({ $isActive }) => ($isActive ? 'visible' : 'hidden')};
  transition: all 0.3s ease-in-out;
`;
