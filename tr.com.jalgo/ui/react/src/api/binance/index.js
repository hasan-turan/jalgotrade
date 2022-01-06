import SocketClient from "@lib/socket-client";
import { useRef } from "react";
import api from "..";
const path = "/binance";

const binance = {
  token: "123456",
  wsUrl: "ws://localhost:8080/binance",
  apiUrl: "http://localhost:8080/jalgo/binance",
  ping: async () => {
    return await api.get(path + "/ping");
  },
  kline: async (parity, interval, environment, messageHandler) => {
    var url = `${binance.wsUrl}/kline?token=${binance.token}&parity=${parity}&interval=${interval}&environment=${environment}`;
    return await new SocketClient(url, messageHandler);
  },
  exchangeInfo: async (parities) => {
    return await api.get(path + "/exchangeInfo", {
      data: parities,
    });
  },
};
export default binance;
