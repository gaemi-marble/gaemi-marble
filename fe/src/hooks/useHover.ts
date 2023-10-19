import {
  MutableRefObject,
  useCallback,
  useEffect,
  useRef,
  useState,
} from 'react';

type HoverReturnType<T extends HTMLElement> = {
  hoverRef: MutableRefObject<T | null>;
  isHover: boolean;
};

export default function useHover<T extends HTMLElement>(): HoverReturnType<T> {
  const hoverRef = useRef<T>(null);
  const [isHover, setIsHover] = useState(false);

  const onMouseEnter = useCallback(() => {
    setIsHover(true);
  }, []);

  const onMouseLeave = useCallback(() => {
    setIsHover(false);
  }, []);

  useEffect(() => {
    const currentRef = hoverRef.current;

    currentRef?.addEventListener('mouseenter', onMouseEnter);
    currentRef?.addEventListener('mouseleave', onMouseLeave);

    return () => {
      currentRef?.removeEventListener('mouseenter', onMouseEnter);
      currentRef?.removeEventListener('mouseleave', onMouseLeave);
    };
  }, [onMouseEnter, onMouseLeave]);

  return { hoverRef, isHover };
}
