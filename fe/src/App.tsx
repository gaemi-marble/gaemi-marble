import { router } from '@router/router';
import { GlobalStyle } from '@styles/GlobalStyle';
import { ModalRoot } from '@styles/common';
import { theme } from '@styles/designSystem';
import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <RouterProvider router={router} />
      <ModalRoot id="modal-root" />
    </ThemeProvider>
  );
}
