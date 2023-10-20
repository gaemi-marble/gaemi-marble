import { stockList } from '@store/constants';
import { StockType } from '@store/type';
import { styled } from 'styled-components';

type PlayerStockProps = {
  stockInfo: StockType;
};

export default function PlayerStock({ stockInfo }: PlayerStockProps) {
  // Memo: id가 아닌 name으로 비교하기로 결정됨
  const stockImgSrc = stockList.find((stock) => stock.id === stockInfo.id)
    ?.imgSrc;

  return (
    <StockImgWrapper>
      <StockImg src={stockImgSrc} />
    </StockImgWrapper>
  );
}

const StockImgWrapper = styled.li`
  width: 3rem;
  border-radius: ${({ theme }) => theme.radius.small};
  background-color: ${({ theme }) => theme.color.accentText};
`;

const StockImg = styled.img`
  width: 3rem;
`;
