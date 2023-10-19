import { styled } from 'styled-components';

export const Page = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

export const ModalRoot = styled.div`
  position: fixed;
  top: 0;
  z-index: 100;
`;

export const Dim = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: ${({ theme: { color } }) => color.neutralOverlay};
  z-index: -1;
`;
