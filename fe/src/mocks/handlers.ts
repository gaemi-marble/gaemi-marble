// src/mocks/handlers.js
import { rest } from 'msw';
import { API_END_POINT } from '../api/constants';

export const handlers = [
  rest.post(API_END_POINT.SIGNUP, (req, res, ctx) => {
    console.log(req);

    return res(ctx.status(201));
  }),
];
