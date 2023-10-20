import { StockType } from '@store/type';

export type PlayerType = {
  playerId: string;
  order: number;
  isReady: boolean;
  location: number;
  userStatusBoard: UserStatusType;
};

type UserStatusType = {
  cashAsset: number;
  stockAsset: number;
  totalAsset: number;
  stockList: StockType[];
};

export type PlayerActionType = {
  enter: EnterPayloadType[];
  ready: ReadyPayloadType;
  userStatusBoard: UserStatusPayloadType;
  cell: CellPayloadType;
};

export type EnterPayloadType = {
  order: number;
  playerId: string;
};

export type ReadyPayloadType = {
  playerId: string;
  isReady: true;
};

export type UserStatusPayloadType = {
  playerId: string;
  userStatusBoard: UserStatusType;
};

export type CellPayloadType = {
  playerId: string;
  location: number;
  salary: number;
  dividend: number;
};
