import { Icon } from '@components/icon/Icon';
import useGetSocketUrl from '@hooks/useGetSocketUrl';
import { usePlayerIdValue } from '@store/index';
import { EmoteNameType } from '@store/reducer/type';
import debounce from 'lodash.debounce';
import useWebSocket from 'react-use-websocket';
import { styled } from 'styled-components';
import { EMOTE_DEBOUNCE_DELAY, EMOTE_LIST } from './constants';

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
    EMOTE_DEBOUNCE_DELAY,
    { leading: true, trailing: false }
  );

  return (
    <Panel $isActive={isActive}>
      {EMOTE_LIST.map((emote) => (
        <Icon
          name={emote}
          size="4rem"
          onClick={() => debounceSendEmote(emote)}
        />
      ))}
    </Panel>
  );
}

const Panel = styled.div<{ $isActive: boolean }>`
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
