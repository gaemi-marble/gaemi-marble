import { atomWithStorage } from 'jotai/utils';

export const accessTokenAtom = atomWithStorage('accessToken', '');
export const refreshTokenAtom = atomWithStorage('refreshToken', '');
