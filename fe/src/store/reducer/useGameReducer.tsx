import useMoveToken from '@hooks/useMoveToken';
import { useReducerAtom } from 'jotai/utils';
import {
  CellPayloadType,
  DicePayloadType,
  EndTurnPayloadType,
  EnterPayloadType,
  EventResultPayloadType,
  EventsPayloadType,
  GameActionType,
  PlayerStatusType,
  GoldCardPayloadType,
  PrisonDicePayloadType,
  ReadyPayloadType,
  StartPayloadType,
  StatusBoardPayloadType,
  TeleportPayloadType,
  UserStatusPayloadType,
  CurrentPlayerPayloadType,
  LocationsPayloadType,
  GameOverPayloadType,
} from './type';
import { gameAtom } from '.';

export default function useGameReducer() {
  const moveToken = useMoveToken();

  const [gameInfo, dispatch] = useReducerAtom(
    gameAtom,
    (
      prev,
      action: {
        type: keyof GameActionType;
        payload: GameActionType[keyof GameActionType];
      }
    ) => {
      switch (action.type) {
        case 'start': {
          const payload = action.payload as StartPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              firstPlayerId: payload.playerId,
              currentPlayerId: payload.playerId,
              isPlaying: true,
            },
          };
        }

        case 'endTurn': {
          const payload = action.payload as EndTurnPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              currentPlayerId: payload.nextPlayerId,
              isMoveFinished: false,
              isArrived: false,
            },
          };
        }

        case 'dice': {
          const payload = action.payload as DicePayloadType;
          const { dice1, dice2 } = payload;

          return {
            ...prev,
            game: {
              ...prev.game,
              dice: [dice1, dice2],
              isArrived: false,
            },
          };
        }

        case 'enter': {
          return {
            ...prev,
            players: prev.players.map((player) => {
              const payload = action.payload as EnterPayloadType[];

              const currentPlayer = payload.find(
                (currentPlayer) => currentPlayer.order === player.order
              );

              if (!currentPlayer) {
                return player;
              }

              const { playerId, isReady } = currentPlayer;

              return {
                ...player,
                playerId: playerId,
                isReady: isReady,
              };
            }),
          };
        }

        case 'ready': {
          return {
            ...prev,
            players: prev.players.map((player) => {
              const payload = action.payload as ReadyPayloadType;

              if (player.playerId !== payload.playerId) {
                return player;
              }

              return {
                ...player,
                isReady: payload.isReady,
              };
            }),
          };
        }

        case 'userStatusBoard': {
          return {
            ...prev,
            players: prev.players.map((player) => {
              const payload = action.payload as UserStatusPayloadType;

              if (player.playerId !== payload.playerId) {
                return player;
              }

              return {
                ...player,
                userStatusBoard: payload.userStatusBoard,
              };
            }),
          };
        }

        case 'cell': {
          const payload = action.payload as CellPayloadType;
          const playerStatus =
            payload.location === 6
              ? 'prison'
              : payload.location === 18
              ? 'teleport'
              : 'default';

          return {
            ...prev,
            game: {
              ...prev.game,
              isMoveFinished: true,
            },
            players: prev.players.map((player) => {
              const { salary, dividend } = payload;
              const bonus = salary + dividend;

              if (player.playerId !== payload.playerId) {
                return player;
              }

              return {
                ...player,
                location: payload.location,
                userStatusBoard: {
                  ...player.userStatusBoard,
                  cashAsset: player.userStatusBoard.cashAsset + bonus,
                },
                gameBoard: {
                  ...player.gameBoard,
                  status: playerStatus as PlayerStatusType,
                },
              };
            }),
          };
        }

        case 'statusBoard': {
          const payload = action.payload as StatusBoardPayloadType;

          return {
            ...prev,
            stocks: prev.stocks.map((stock) => {
              const newStock = payload.stockStatusBoard.find(
                (resStock) => resStock.name === stock.name
              );

              if (!newStock) {
                return stock;
              }

              return {
                ...stock,
                quantity: newStock.quantity,
                price: newStock.price,
              };
            }),
          };
        }

        case 'events': {
          const payload = action.payload as EventsPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              timer: payload.timer,
              eventList: [...payload.events],
              isArrived: false,
            },
          };
        }

        case 'eventResult': {
          const payload = action.payload as EventResultPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              eventResult: payload.name,
              isArrived: false,
            },
          };
        }

        case 'goldCard': {
          const payload = action.payload as GoldCardPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              goldCardInfo: {
                title: payload.title,
                description: payload.description,
              },
            },
          };
        }

        case 'prisonDice': {
          const payload = action.payload as PrisonDicePayloadType;
          const { dice1, dice2 } = payload;

          if (!payload.hasEscaped) {
            return {
              ...prev,
              game: {
                ...prev.game,
                dice: [dice1, dice2],
                isArrived: false,
              },
              players: prev.players.map((player) => {
                if (player.playerId !== payload.playerId) {
                  return player;
                }
                return {
                  ...player,
                  gameBoard: {
                    ...player.gameBoard,
                    hasEscaped: false,
                  },
                };
              }),
            };
          }

          return {
            ...prev,
            game: {
              ...prev.game,
              dice: [dice1, dice2],
              isArrived: false,
            },
            players: prev.players.map((player) => {
              if (player.playerId !== payload.playerId) {
                return player;
              }
              return {
                ...player,
                gameBoard: {
                  ...player.gameBoard,
                  hasEscaped: true,
                },
              };
            }),
          };
        }

        case 'teleport': {
          const payload = action.payload as TeleportPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              teleportLocation: payload.location,
              isArrived: false,
            },
          };
        }

        case 'currentPlayer': {
          const payload = action.payload as CurrentPlayerPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              currentPlayerId: payload.playerId,
            },
          };
        }

        // Memo: 수정 필요
        case 'locations': {
          return {
            ...prev,
            game: {
              ...prev.game,
              isPlaying: true,
            },
            players: prev.players.map((player) => {
              const payload = action.payload as LocationsPayloadType[];

              const currentPlayer = payload.find(
                (currentPlayer) => currentPlayer.playerId === player.playerId
              );

              if (!currentPlayer) {
                return player;
              }

              const { location } = currentPlayer;

              moveToken({
                diceCount: location,
                playerGameBoardData: player.gameBoard,
                type: 'reconnect',
              });

              return {
                ...player,
                location: location,
              };
            }),
          };
        }

        case 'gameOver': {
          const payload = action.payload as GameOverPayloadType;

          return {
            ...prev,
            game: {
              ...prev.game,
              isPlaying: false,
              ranking: [...payload.ranking],
            },
          };
        }

        default: {
          return prev;
        }
      }
    }
  );

  return { gameInfo, dispatch };
}
