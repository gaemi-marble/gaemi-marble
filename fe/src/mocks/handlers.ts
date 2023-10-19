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
      ctx.json({
        playerId: 'antman',
      }),
      ctx.set({
        Authorization:
          'eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbklkIjoiZnVzZTEyIiwiZXhwIjoxNjk3NTk5MTM5fQ.LMkskjLUkPlUMbpzqmraYY3zQr0d5wQETGyecPbgdbpe7dsMVTOe6_kZMczTMfA2kE1NI9OycNvA4b_dlgK_jw',
        'Refresh-Token':
          'eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2OTc2MDA5Mzl9.6sbeiPzyOM1G8nUJ_4UzdNBWTTUgwPJeQN3aPQmCDi83ncoMUf5JDRDaSIHN6yFf3sDwvkgO7U9-L6s9lGOq-Q',
      })
    );
  }),

  rest.post(API_END_POINT.LOGOUT, (_, res, ctx) => {
    return res(ctx.status(200));
  }),

  rest.post(API_END_POINT.GAMES, (_, res, ctx) => {
    return res(
      ctx.status(201),
      ctx.json({
        gameId: 1,
      })
    );
  }),
];
