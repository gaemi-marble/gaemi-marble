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

  const handleMouseEnter = useCallback(() => {
    setIsHover(true);
  }, []);

  const handleMouseLeave = useCallback(() => {
    setIsHover(false);
  }, []);

  useEffect(() => {
    const currentRef = hoverRef.current;

    currentRef?.addEventListener('mouseenter', handleMouseEnter);
    currentRef?.addEventListener('mouseleave', handleMouseLeave);

    return () => {
      currentRef?.removeEventListener('mouseenter', handleMouseEnter);
      currentRef?.removeEventListener('mouseleave', handleMouseLeave);
    };
  }, [handleMouseEnter, handleMouseLeave]);

  return { hoverRef, isHover };
}
