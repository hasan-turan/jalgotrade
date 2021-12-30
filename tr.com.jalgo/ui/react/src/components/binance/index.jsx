import React, { useEffect, useRef } from "react";
//Ctrl + Click (For goto definition install "Smart JS GoTo" extension otherwise goto definition with aliases wont work )
import binance from "@api/binance";
import SocketClient from "@lib/socket-client";

const Binance = () => {
  let ohlcSocket = useRef(null);
  const parity = "BTCUSDT";
  const interval = "4h";
  const isTestnet = false;
  useEffect(() => {
    getLiveData();
  });

  const getLiveData = () => {
    ohlcSocket.current = new SocketClient(
      binance.wsUrl,
      `/${parity}/${interval}/${isTestnet}`,
      socketMessageHandler
    );
  };
  const socketMessageHandler = (message) => {
    console.log("Message received-->", message);
  };
  return <React.Fragment>Binance Page</React.Fragment>;
};

export default Binance;
