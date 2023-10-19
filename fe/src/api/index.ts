import { API_END_POINT } from './constants';
import { fetcher } from './fetcher';

export const postSignup = async (playerId: string, password: string) => {
  const res = await fetcher.post(API_END_POINT.SIGNUP, {
    playerId,
    password,
  });

  return res;
};

export const postSignin = async (playerId: string, password: string) => {
  const res = await fetcher.post(API_END_POINT.SIGNIN, {
    playerId,
    password,
  });

  return res;
};

export const postLogout = async () => {
  const res = await fetcher.post(API_END_POINT.LOGOUT);
  return res;
};
