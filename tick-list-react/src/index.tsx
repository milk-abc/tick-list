import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Link } from "react-router-dom";
import { createStore } from "redux";

const root = document.getElementById("root");
const store = createStroe();
if (root) {
  createRoot(root).render(
    <Provider store={store}>
      <App />
    </Provider>
  );
}
