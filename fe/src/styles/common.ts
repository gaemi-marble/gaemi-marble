import { styled } from 'styled-components';

export const Page = styled.div`
  width: 100vw;
  height: 100vh;
`;

// TODO: Container 네이밍 변경
export const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 16px;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;

export const Title = styled.h1`
  font-size: ${({ theme: { fontSize } }) => fontSize.xLarge};
`;

// TODO: Input 공통 컴포넌트로 만들기
export const Input = styled.input`
  padding: 0 6px;
  border-radius: ${({ theme: { radius } }) => radius.small};
  color: ${({ theme: { color } }) => color.neutralText};
`;

// TODO: Button 공통 컴포넌트로 만들기
export const Button = styled.button`
  width: 200px;
  height: 100px;
  border: ${({ theme: { color } }) => `1px solid ${color.accentBorder}`};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  font-size: ${({ theme: { fontSize } }) => fontSize.large};
  cursor: pointer;

  &:hover {
    border-color: ${({ theme: { color } }) => color.neutralBorderStrong};
    color: ${({ theme: { color } }) => color.neutralTextStrong};
    background-color: ${({ theme: { color } }) => color.neutralBackground};
  }
`;
