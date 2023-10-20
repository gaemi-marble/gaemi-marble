import antBasic from '@assets/icon/antBasic.svg?react';
import antFire from '@assets/icon/antFire.svg?react';
import antHoney from '@assets/icon/antHoney.svg?react';
import antWater from '@assets/icon/antWater.svg?react';
import exit from '@assets/icon/exit.svg?react';
import plus from '@assets/icon/plus.svg?react';
import sample from '@assets/icon/sample.svg?react';

export const icons = {
  plus,
  sample,
  exit,
  antBasic,
  antFire,
  antWater,
  antHoney,
};

export type IconsType = keyof typeof icons;
