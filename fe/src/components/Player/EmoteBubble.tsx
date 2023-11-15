import { Icon } from '@components/icon/Icon';
import { EmoteNameType } from '@store/reducer/type';
import { styled } from 'styled-components';

type EmoteBubbleProps = {
  emoteName: EmoteNameType;
};

export default function EmoteBubble({ emoteName }: EmoteBubbleProps) {
  return (
    <Bubble>
      <Icon name={emoteName} size="3rem" />
    </Bubble>
  );
}

const Bubble = styled.div`
  width: 4rem;
  height: 4rem;
  position: absolute;
  top: -3rem;
  right: 0.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 25%;
  background: #eee;

  &:after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 0;
    border: 15px solid transparent;
    border-top-color: #eee;
    border-bottom: 0;
    border-left: 0;
    margin-left: -15px;
    margin-bottom: -15px;
  }
`;
