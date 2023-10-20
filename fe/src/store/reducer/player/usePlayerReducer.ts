import { useReducerAtom } from 'jotai/utils';
import {
  CellPayloadType,
  EnterPayloadType,
  PlayerActionType,
  ReadyPayloadType,
  UserStatusPayloadType,
} from './type';
import { playersAtom } from '.';

export default function usePlayerReducer() {
  const [playersInfo, dispatch] = useReducerAtom(
    playersAtom,
    (
      prev,
      action: {
        type: keyof PlayerActionType;
        payload: PlayerActionType[keyof PlayerActionType];
      }
    ) => {
      if (action.type === 'enter') {
        const payload = action.payload as EnterPayloadType[];

        return prev.map((player, index) => {
          if (!payload[index]) {
            return player;
          }

          return {
            ...player,
            playerId: payload[index].playerId,
          };
        });
      } else if (action.type === 'ready') {
        const payload = action.payload as ReadyPayloadType;
        // Memo: 게임 준비 관련 로직 추가 시 주석 제거
        console.log(payload);

        return prev.map((player) => {
          if (player.playerId !== payload.playerId) {
            return player;
          }

          return {
            ...player,
            isReady: payload.isReady,
          };
        });
      } else if (action.type === 'userStatusBoard') {
        const payload = action.payload as UserStatusPayloadType;

        return prev.map((player) => {
          if (player.playerId !== payload.playerId) {
            return player;
          }

          return {
            ...player,
            userStatusBoard: payload.userStatusBoard,
          };
        });
      } else if (action.type === 'cell') {
        const payload = action.payload as CellPayloadType;
        const bonus = payload.salary + payload.dividend;

        return prev.map((player) => {
          if (player.playerId !== payload.playerId) {
            return player;
          }

          return {
            ...player,
            location: payload.location,
            userStatusBoard: {
              ...player.userStatusBoard,
              cashAsset: player.userStatusBoard.cashAsset + bonus,
              totalAsset: player.userStatusBoard.totalAsset + bonus,
            },
          };
        });
      } else {
        return prev;
      }
    }
  );

  return { playersInfo, dispatch };
}
