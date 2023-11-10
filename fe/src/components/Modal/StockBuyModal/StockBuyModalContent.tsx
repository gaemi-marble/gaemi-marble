import { cellImageMap } from '@assets/images';
import { Icon } from '@components/icon/Icon';
import useGetSocketUrl from '@hooks/useGetSocketUrl';
import { usePlayerIdValue } from '@store/index';
import { useGameValue, useSetGameInfo } from '@store/reducer';
import { addCommasToNumber } from '@utils/index';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import useWebSocket from 'react-use-websocket';
import { styled } from 'styled-components';

export default function StockBuyModalContent() {
  const { gameId } = useParams();
  const playerId = usePlayerIdValue();
  const { game, players, stocks } = useGameValue();
  const setGameInfo = useSetGameInfo();
  const [purchaseQuantity, setPurchaseQuantity] = useState(0);
  const socketUrl = useGetSocketUrl();
  const { sendJsonMessage } = useWebSocket(socketUrl, {
    share: true,
  });

  const handleClose = () => {
    setGameInfo((prev) => {
      return {
        ...prev,
        isArrived: false,
      };
    });
  };

  const handleBuyStock = () => {
    const message = {
      gameId,
      playerId,
      type: 'buy',
      stockName: currentStock?.name,
      quantity: purchaseQuantity,
    };
    sendJsonMessage(message);

    handleClose();
  };

  const handlePurchaseQuantity = (perQuantity: number) => {
    setPurchaseQuantity((prev) => (prev += perQuantity));
  };

  const currentPlayer = players.find(
    (player) => player.playerId === game.currentPlayerId
  );

  const currentLocation = currentPlayer?.location;

  const currentStock = stocks.find(
    (stock) => stock.location === currentLocation
  )!;
  const { logo, name, theme, price, quantity } = currentStock;

  const cashAsset = currentPlayer!.userStatusBoard.cashAsset;
  const totalPrice = purchaseQuantity * currentStock!.price;
  const perPrice = 5 * currentStock!.price;
  const isOverLimit =
    purchaseQuantity >= currentStock!.quantity ||
    totalPrice + perPrice > cashAsset;
  const isUnderLimit = purchaseQuantity <= 0;

  return (
    <ModalContent>
      <StockLogo src={cellImageMap[logo]} />
      <StockInfoTable>
        <tbody>
          <tr>
            <th>주식 이름</th>
            <td>{name}</td>
          </tr>
          <tr>
            <th>주식 테마</th>
            <td>{theme}</td>
          </tr>
          <tr>
            <th>주당 가격</th>
            <td>{addCommasToNumber(price)}</td>
          </tr>
          <tr>
            <th>잔여 수량</th>
            <td>{quantity - purchaseQuantity}</td>
          </tr>
          <tr>
            <th>보유 현금</th>
            <td>{addCommasToNumber(cashAsset)}</td>
          </tr>
          <tr>
            <th>매수 수량</th>
            <td>{purchaseQuantity}</td>
          </tr>
          <tr>
            <th>총 가격</th>
            <td>{addCommasToNumber(totalPrice)}</td>
          </tr>
        </tbody>
      </StockInfoTable>
      {playerId === game.currentPlayerId && (
        <ButtonWrapper>
          <Button className="close" onClick={handleClose}>
            닫기
          </Button>
          <PurchaseQuantityWrapper>
            <AdjustButton
              disabled={isOverLimit}
              onClick={() => handlePurchaseQuantity(5)}
            >
              <Icon name="arrowUp" />
            </AdjustButton>
            <PurchaseQuantity>{purchaseQuantity}</PurchaseQuantity>
            <AdjustButton
              disabled={isUnderLimit}
              onClick={() => handlePurchaseQuantity(-5)}
            >
              <Icon name="arrowDown" />
            </AdjustButton>
          </PurchaseQuantityWrapper>
          <Button className="purchase" onClick={handleBuyStock}>
            매수
          </Button>
        </ButtonWrapper>
      )}
    </ModalContent>
  );
}

const ModalContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
`;

const StockInfoTable = styled.table`
  border: 1px solid ${({ theme }) => theme.color.neutralBorderStrong};
  font-size: ${({ theme }) => theme.fontSize.sMedium};

  th,
  td {
    padding: 0.5rem 3rem;
    border: 1px solid ${({ theme }) => theme.color.neutralBorderStrong};
  }
`;

const StockLogo = styled.img`
  width: 3rem;
`;

const ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 5rem;
`;

const Button = styled.button`
  width: 7rem;
  height: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border: ${({ theme: { color } }) => `1px solid ${color.accentBorder}`};
  border-radius: ${({ theme: { radius } }) => radius.medium};
  font-size: ${({ theme: { fontSize } }) => fontSize.medium};
  cursor: pointer;

  &.close {
    color: ${({ theme: { color } }) => color.accentText};
    background-color: ${({ theme: { color } }) => color.systemWarning};
  }

  &.purchase {
    color: ${({ theme: { color } }) => color.accentText};
    background-color: ${({ theme: { color } }) => color.accentBackground};
  }
`;

const PurchaseQuantityWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const PurchaseQuantity = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: ${({ theme: { fontSize } }) => fontSize.medium};
`;

const AdjustButton = styled.button``;
