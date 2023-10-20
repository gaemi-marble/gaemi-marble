import { styled } from 'styled-components';
import Cell from './Cell';

export default function GameBoard() {
  return (
    <Board>
      <Line1>
        <Cell logo="start" name="start" />
        <Cell
          theme="fashion"
          name="무신사"
          logo=""
          sharePrice={400000}
          remainingStock={100}
        />
        <Cell
          theme="food"
          name="농심"
          logo=""
          sharePrice={500000}
          remainingStock={100}
        />
        <Cell
          theme="travel"
          name="하나투어"
          logo=""
          sharePrice={600000}
          remainingStock={100}
        />
        <Cell
          theme="construction"
          name="GS건설"
          logo=""
          sharePrice={700000}
          remainingStock={100}
        />
        <Cell
          theme="it"
          name="코드스쿼드"
          logo=""
          sharePrice={800000}
          remainingStock={100}
        />
      </Line1>
      <Line2>
        <Cell logo="jail" name="유치장" />
        <Cell
          theme="military"
          name="한화디펜스"
          logo=""
          sharePrice={900000}
          remainingStock={100}
        />
        <Cell
          theme="pharmaceutical"
          name="화이자"
          logo=""
          sharePrice={1000000}
          remainingStock={100}
        />
        <Cell name="황금카드" logo="goldCard" />
        <Cell
          theme="elonMusk"
          name="트위터"
          logo="twitter"
          sharePrice={1100000}
          remainingStock={100}
        />
        <Cell
          theme="it"
          name="구글"
          logo="google"
          sharePrice={1200000}
          remainingStock={100}
        />
      </Line2>
      <Line3>
        <Cell logo="goodNews" name="호재" />
        <Cell
          theme="pharmaceutical"
          name="삼성바이오로직스"
          logo=""
          sharePrice={1300000}
          remainingStock={100}
        />
        <Cell
          theme="food"
          name="맥도날드"
          logo="mcdonalds"
          sharePrice={1400000}
          remainingStock={100}
        />
        <Cell logo="tax" name="세금" />
        <Cell
          theme="travel"
          name="대한항공"
          logo=""
          sharePrice={1500000}
          remainingStock={100}
        />
        <Cell
          theme="construction"
          name="현대건설"
          logo=""
          sharePrice={1600000}
          remainingStock={100}
        />
      </Line3>
      <Line4>
        <Cell logo="rocket" name="순간이동" />
        <Cell
          theme="fashion"
          name="에르메스"
          logo="hermes"
          sharePrice={1700000}
          remainingStock={100}
        />
        <Cell
          theme="elonMusk"
          name="테슬라"
          logo="tesla"
          sharePrice={1800000}
          remainingStock={100}
        />
        <Cell logo="goldCard" name="황금카드" />
        <Cell
          theme="military"
          name="스타크 인더스트리"
          logo=""
          sharePrice={1900000}
          remainingStock={100}
        />
        <Cell
          theme="it"
          name="애플"
          logo="apple"
          sharePrice={2000000}
          remainingStock={100}
        />
      </Line4>
    </Board>
  );
}

const Board = styled.div`
  width: 42rem;
  height: 42rem;
  margin: 0 auto;
  border-color: ${({ theme: { color } }) => color.accentText};
  position: relative;
`;

const Line1 = styled.div`
  display: flex;
  flex-direction: column-reverse;
  position: absolute;
  top: 6rem;
  left: 0;

  div {
    border-top: none;
  }
`;

const Line2 = styled.div`
  display: flex;
  flex-direction: row;
  position: absolute;
  top: 0;

  div {
    border-right: none;
  }
`;

const Line3 = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  right: 0;

  div {
    border-bottom: none;
  }
`;

const Line4 = styled.div`
  display: flex;
  flex-direction: row-reverse;
  position: absolute;
  bottom: 0;
  left: 6rem;

  div {
    border-left: none;
  }
`;
