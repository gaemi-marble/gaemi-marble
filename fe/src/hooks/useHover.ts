import { MutableRefObject, useEffect, useRef, useState } from 'react';

type HoverReturnType<T extends HTMLElement> = {
  hoverRef: MutableRefObject<T | null>;
  isHover: boolean;
};

export default function useHover<T extends HTMLElement>(): HoverReturnType<T> {
  const hoverRef = useRef<T>(null);
  const [isHover, setIsHover] = useState(false);

  useEffect(() => {
    const onMouseEnter = () => setIsHover(true);
    const onMouseLeave = () => setIsHover(false);

    hoverRef.current?.addEventListener('mouseenter', onMouseEnter);
    hoverRef.current?.addEventListener('mouseleave', onMouseLeave);
  }, []);

  return { hoverRef, isHover };
}
