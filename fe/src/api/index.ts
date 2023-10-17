import { API_END_POINT } from './constants';
import { fetcher } from './fetcher';

export const signup = async (playerId: string, password: string) => {
  const res = await fetcher.post(API_END_POINT.SIGNUP, {
    playerId,
    password,
  });
  return res;
};

export const signin = async (playerId: string, password: string) => {
  const res = await fetcher.post(API_END_POINT.SIGNIN, {
    playerId,
    password,
  });
  return res;
};
