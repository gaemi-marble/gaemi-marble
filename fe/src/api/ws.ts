// Memo: 웹 소켓 연결 전 임시 api
import { fetcher } from './fetcher';

type ReadyRequestType = {
  type: string;
  gameId: number;
  playerId: string;
  isReady: boolean;
};

type BuyRequestType = {
  type: string;
  gameId: number;
  playerId: string;
  stockName: string;
  count: number;
};

type SellRequestType = {
  type: string;
  gameId: number;
  playerId: string;
  stockList: [
    {
      name: string;
      count: number;
    },
  ];
};

export const postEnter = async (playerId: string) => {
  const res = await fetcher.post('/enter', playerId);
  return res;
};

export const postReady = async (req: ReadyRequestType) => {
  const res = await fetcher.post('/ready', req);
  return res;
};

export const postBuy = async (req: BuyRequestType) => {
  const res = await fetcher.post('/buy', req);
  return res;
};

export const postSell = async (req: SellRequestType) => {
  const res = await fetcher.post('/sell', req);
  return res;
};

export const getCell = async () => {
  const res = await fetcher.get('/cell');
  return res;
};
