import "@css/app.css";
import "primereact/resources/themes/bootstrap4-light-blue/theme.css"; //theme
//import "primereact/resources/themes/bootstrap4-dark-blue/theme.css";  //theme

import "primereact/resources/primereact.min.css"; //core css
import "primeicons/primeicons.css"; //icons
import { Splitter, SplitterPanel } from "primereact/splitter";
import { PanelMenu } from "primereact/panelmenu";
import PrimeReact from "primereact/api";

import { Routes, Route } from "react-router-dom";
import About from "./about";
import Home from "./home";
import Binance from "./binance";
import React from "react";

import { useNavigate } from "react-router-dom";

PrimeReact.ripple = true;

const App = (props) => {
  const navigate = useNavigate();

  const navigateToPage = (path) => {
    console.log("Path",path);
    navigate(path);
  };
  const items = [
    {
      label: "Home",
      icon: "pi pi-fw pi-file",
      url: "/home",
    },
    {
      label: "Binance",
      icon: "pi pi-fw pi-file",
      items: [
        {
          label: "Binance test",
          icon: "pi pi-fw pi-plus",
          command: () => {
            navigateToPage("/binance");
          },
        },
      ],
    },
  ];
  return (
    <React.Fragment>
      <Splitter className="x-splitter">
        <SplitterPanel size={20} className="x-left-menu">
          <PanelMenu model={items} />
        </SplitterPanel>
        <SplitterPanel size={80}>
          <Routes>
            <Route exact path="/" element={<Home />} />

            <Route exact path="/binance" element={<Binance />} />

            <Route exact path="/about" element={<About />} />
          </Routes>
        </SplitterPanel>
      </Splitter>
    </React.Fragment>
  );
};

export default App;
