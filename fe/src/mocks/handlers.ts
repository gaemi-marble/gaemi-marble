// src/mocks/handlers.js
import { rest } from 'msw';
import { API_END_POINT } from '../api/constants';

export const handlers = [
  rest.post(API_END_POINT.SIGNUP, (_, res, ctx) => {
    return res(ctx.status(201));
  }),

  rest.post(API_END_POINT.SIGNIN, (_, res, ctx) => {
    return res(
      ctx.status(200),
      ctx.json(playerId),
      ctx.set({
        Authorization: 'Bearer access-token',
        'Refresh-Token': 'Bearer refresh-token',
      })
    );
  }),
];

const playerId = {
  playerId: 'antman',
};
