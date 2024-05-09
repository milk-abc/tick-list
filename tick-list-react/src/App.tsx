import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import routers from "@/router";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        {routers.map((r, key) => {
          console.log("hhh", r);
          return <Route element={<r.component />} key={key} path={r.path} />;
        })}
      </Routes>
    </BrowserRouter>
  );
}
export default App;
