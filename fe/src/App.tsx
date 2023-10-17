import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { router } from './router/router';
import { GlobalStyle } from './styles/GlobalStyle';
import { Page } from './styles/common';
import { theme } from './styles/designSystem';

export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Page>
        <RouterProvider router={router} />
      </Page>
    </ThemeProvider>
  );
}
