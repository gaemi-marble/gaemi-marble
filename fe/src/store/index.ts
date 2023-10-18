import { atom, useAtomValue, useSetAtom } from 'jotai';

const accessTokenAtom = atom(localStorage.getItem('accessToken') || '');
const accessTokenAtomWithStorage = atom(
  (get) => get(accessTokenAtom),
  (_, set, newAccessToken: string) => {
    set(accessTokenAtom, newAccessToken);
    localStorage.setItem('accessToken', newAccessToken);
  }
);
const refreshTokenAtom = atom(localStorage.getItem('refreshToken') || '');
const refreshTokenAtomWithStorage = atom(
  (get) => get(refreshTokenAtom),
  (_, set, newRefreshToken: string) => {
    set(refreshTokenAtom, newRefreshToken);
    localStorage.setItem('refreshToken', newRefreshToken);
  }
);

export const useSetAccessToken = () => useSetAtom(accessTokenAtomWithStorage);
export const useSetRefreshToken = () => useSetAtom(refreshTokenAtomWithStorage);

export const useAuth = () => useAtomValue(accessTokenAtom);
