import { styled } from 'styled-components';
import Cell from './Cell';

export default function GameBoard() {
  return (
    <Board>
      <Line1>
        <Cell logo="start" name="start" />
        <Cell
          theme="it"
          name="코드스쿼드"
          logo="codesquad"
          sharePrice={400000}
        />
        <Cell
          theme="fashion"
          name="무신사"
          logo="musinsa"
          sharePrice={500000}
        />
        <Cell
          theme="travel"
          name="하나투어"
          logo="hanatour"
          sharePrice={600000}
        />
        <Cell
          theme="construction"
          name="GS건설"
          logo="gs"
          sharePrice={700000}
        />
        <Cell theme="food" name="농심" logo="nongshim" sharePrice={800000} />
      </Line1>
      <Line2>
        <Cell logo="jail" name="유치장" />
        <Cell
          theme="construction"
          name="현대건설"
          logo="hyundai"
          sharePrice={900000}
        />
        <Cell
          theme="military"
          name="한화디펜스"
          logo="hanwha"
          sharePrice={1000000}
        />
        <Cell name="황금카드" logo="goldCard" />
        <Cell
          theme="travel"
          name="대한항공"
          logo="koreanAir"
          sharePrice={1100000}
        />
        <Cell
          theme="elonMusk"
          name="트위터"
          logo="twitter"
          sharePrice={1200000}
        />
      </Line2>
      <Line3>
        <Cell logo="goodNews" name="호재" />
        <Cell
          theme="pharmaceutical"
          name="삼성바이오로직스"
          logo="samsungBio"
          sharePrice={1300000}
        />
        <Cell theme="it" name="구글" logo="google" sharePrice={1400000} />
        <Cell logo="tax" name="세금" />
        <Cell
          theme="fashion"
          name="에르메스"
          logo="hermes"
          sharePrice={1500000}
        />
        <Cell
          theme="food"
          name="맥도날드"
          logo="mcdonalds"
          sharePrice={1600000}
        />
      </Line3>
      <Line4>
        <Cell logo="rocket" name="순간이동" />
        <Cell
          theme="elonMusk"
          name="테슬라"
          logo="tesla"
          sharePrice={1700000}
        />
        <Cell
          theme="pharmaceutical"
          name="화이자"
          logo="pfizer"
          sharePrice={1800000}
        />
        <Cell logo="goldCard" name="황금카드" />
        <Cell
          theme="military"
          name="스타크 인더스트리"
          logo="starkIndustry"
          sharePrice={1900000}
        />
        <Cell theme="it" name="애플" logo="apple" sharePrice={2000000} />
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
