import logout from '@assets/logout.svg?react';
import plus from '@assets/plus.svg?react';
import sample from '@assets/sample.svg?react';

export const icons = {
  plus,
  sample,
  logout,
};

export type IconsType = keyof typeof icons;
