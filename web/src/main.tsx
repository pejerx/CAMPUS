import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";

import { MantineProvider, createTheme } from "@mantine/core";
import "@mantine/core/styles.css";

import "@shoelace-style/shoelace/dist/themes/light.css";
import "@shoelace-style/shoelace/dist/components/button/button.js";
import "./css/index.css";
import App from "./App";

const theme = createTheme({
  primaryColor: "maroon",
  colors: {
    maroon: [
      "#fff5f5",
      "#ffe3e3",
      "#ffc9c9",
      "#ffa8a8",
      "#ff8787",
      "#ff6b6b",
      "#fa5252",
      "#800000",
      "#660000",
      "#4d0000",
    ],
  },
});

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <BrowserRouter>
      <MantineProvider theme={theme} defaultColorScheme="light">
        <App />
      </MantineProvider>
    </BrowserRouter>
  </StrictMode>
);