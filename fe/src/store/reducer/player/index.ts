import { atom, useAtomValue } from 'jotai';
import { PlayerType } from './type';

// Memo: 사용법 예시
// const { playersInfo, dispatch } = usePlayerReducer();

export const playersAtom = atom<PlayerType[]>([
  {
    playerId: '',
    order: 1,
    isReady: false,
    location: 0,
    userStatusBoard: {
      cashAsset: 0,
      stockAsset: 0,
      totalAsset: 0,
      stockList: [],
    },
  },
  {
    playerId: '',
    order: 2,
    isReady: false,
    location: 0,
    userStatusBoard: {
      cashAsset: 0,
      stockAsset: 0,
      totalAsset: 0,
      stockList: [],
    },
  },
  {
    playerId: '',
    order: 3,
    isReady: false,
    location: 0,
    userStatusBoard: {
      cashAsset: 0,
      stockAsset: 0,
      totalAsset: 0,
      stockList: [],
    },
  },
  {
    playerId: '',
    order: 4,
    isReady: false,
    location: 0,
    userStatusBoard: {
      cashAsset: 0,
      stockAsset: 0,
      totalAsset: 0,
      stockList: [],
    },
  },
]);

export const usePlayersValue = () => useAtomValue(playersAtom);
