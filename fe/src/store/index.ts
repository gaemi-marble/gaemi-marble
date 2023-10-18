import { useAtomValue } from 'jotai';
import { atomWithStorage } from 'jotai/utils';

export const accessTokenAtom = atomWithStorage(
  'accessToken',
  localStorage.getItem('accessToken') || ''
);
export const refreshTokenAtom = atomWithStorage(
  'refreshToken',
  localStorage.getItem('refreshToken') || ''
);

export const useAuth = () => useAtomValue(accessTokenAtom);
