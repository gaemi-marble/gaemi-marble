import { Icon } from '@components/icon/Icon';
import { useState } from 'react';
import { styled } from 'styled-components';
import EmotePanel from './EmotePanel';

export default function Emotes() {
  const [isActive, setIsActive] = useState(false);

  const handleToggleEmotePanel = () => {
    setIsActive((prev) => !prev);
  };

  return (
    <>
      <Button onClick={handleToggleEmotePanel}>
        <Icon name="emote" />
      </Button>
      <EmotePanel isActive={isActive} />
    </>
  );
}

const Button = styled.button`
  width: 50px;
  height: 100px;
  position: absolute;
  bottom: 50%;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 2px solid black;
  border-right: none;
  border-radius: ${({ theme }) => theme.radius.small} 0 0
    ${({ theme }) => theme.radius.small};
  background-color: ${({ theme }) => theme.color.neutralBackground};
`;
