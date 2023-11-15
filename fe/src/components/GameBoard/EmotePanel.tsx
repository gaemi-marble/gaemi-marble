import { Icon } from '@components/icon/Icon';
import useGetSocketUrl from '@hooks/useGetSocketUrl';
import { usePlayerIdValue } from '@store/index';
import { EmoteNameType } from '@store/reducer/type';
import debounce from 'lodash.debounce';
import useWebSocket from 'react-use-websocket';
import { styled } from 'styled-components';

type EmotePanelProps = {
  isActive: boolean;
};

export default function EmotePanel({ isActive }: EmotePanelProps) {
  const playerId = usePlayerIdValue();
  const socketUrl = useGetSocketUrl();
  const { sendJsonMessage } = useWebSocket(socketUrl, {
    share: true,
  });

  const sendEmote = (name: EmoteNameType) => {
    const message = {
      type: 'emoticon',
      playerId,
      name,
    };
    sendJsonMessage(message);
  };

  const debounceSendEmote = debounce(
    (name: EmoteNameType) => sendEmote(name),
    1000,
    { leading: true, trailing: false }
  );

  return (
    <Div $isActive={isActive}>
      <Icon name="hi" size="4rem" onClick={() => debounceSendEmote('hi')} />
      <Icon
        name="angry"
        size="4rem"
        onClick={() => debounceSendEmote('angry')}
      />
      <Icon
        name="laugh"
        size="4rem"
        onClick={() => debounceSendEmote('laugh')}
      />
      <Icon name="cry" size="4rem" onClick={() => debounceSendEmote('cry')} />
      <Icon
        name="celebrate"
        size="4rem"
        onClick={() => debounceSendEmote('celebrate')}
      />
      <Icon
        name="clock"
        size="4rem"
        onClick={() => debounceSendEmote('clock')}
      />
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
