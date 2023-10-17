import { ThemeProvider } from "styled-components";
import { theme } from "./styles/designSystem";
import { GlobalStyle } from "./styles/GlobalStyle";
import { RouterProvider } from "react-router-dom";
import { router } from "./router/router";
import { Page } from "./styles/common";

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
