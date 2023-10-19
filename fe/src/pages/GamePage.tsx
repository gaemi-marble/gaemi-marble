import { styled } from 'styled-components';

export default function GamePage() {
  return (
    <Container>
      <Main>
        <GameBoard>
          <CornerCell>Start</CornerCell>
          <Line1>
            <Cell>1</Cell>
            <Cell>2</Cell>
            <Cell>3</Cell>
            <Cell>4</Cell>
            <Cell>5</Cell>
          </Line1>
          <CornerCell>유치장</CornerCell>
          <Line2>
            <Cell>6</Cell>
            <Cell>7</Cell>
            <Cell>8</Cell>
            <Cell>9</Cell>
            <Cell>10</Cell>
          </Line2>
          <CornerCell>호재</CornerCell>
          <Line3>
            <Cell>12</Cell>
            <Cell>13</Cell>
            <Cell>14</Cell>
            <Cell>15</Cell>
            <Cell>16</Cell>
          </Line3>
          <CornerCell>순간이동</CornerCell>
          <Line4>
            <Cell>18</Cell>
            <Cell>19</Cell>
            <Cell>20</Cell>
            <Cell>21</Cell>
            <Cell>22</Cell>
          </Line4>
        </GameBoard>
      </Main>
    </Container>
  );
}

const Container = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 16px;
  color: ${({ theme: { color } }) => color.accentText};
  background-color: ${({ theme: { color } }) => color.accentPrimary};
`;

const Main = styled.div`
  width: 100%;
  height: 100%;
`;

const GameBoard = styled.div`
  width: 42rem;
  height: 42rem;
  /* border: 1px solid; */
  border-color: ${({ theme: { color } }) => color.accentText};
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const Line1 = styled.div`
  display: flex;
  flex-direction: column-reverse;
  position: absolute;
  top: 6rem;
  left: 0;

  div {
    border-top: none;

    &:nth-child(1) {
      border-bottom: none;
    }
  }
`;

const Line2 = styled.div`
  display: flex;
  flex-direction: row;
  position: absolute;
  top: 0;
  left: 6rem;

  div {
    border-right: none;

    &:nth-child(1) {
      border-left: none;
    }
  }
`;

const Line3 = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 6rem;
  right: 0;

  div {
    border-bottom: none;

    &:nth-child(1) {
      border-top: none;
    }
  }
`;

const Line4 = styled.div`
  display: flex;
  flex-direction: row-reverse;
  position: absolute;
  bottom: 0;
  right: 6rem;

  div {
    border-left: none;

    &:nth-child(1) {
      border-right: none;
    }
  }
`;

const Cell = styled.div`
  width: 6rem;
  height: 6rem;
  border: 1px solid;
  border-color: ${({ theme: { color } }) => color.accentText};
`;

const CornerCell = styled(Cell)`
  position: absolute;

  &:nth-child(1) {
    bottom: 0;
  }

  &:nth-child(3) {
    top: 0;
  }

  &:nth-child(5) {
    top: 0;
    right: 0;
  }

  &:nth-child(7) {
    bottom: 0;
    right: 0;
  }
`;
