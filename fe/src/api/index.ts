import { API_END_POINT } from './constants';
import { fetcher } from './fetcher';

export const signup = (playerId: string, password: string) => {
  const res = fetcher.post(API_END_POINT.SIGNUP, { playerId, password });
  return res;
};
