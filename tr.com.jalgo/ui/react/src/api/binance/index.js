import api from "..";
const path = "/binance";

const binance = {
  wsUrl:"ws://localhost:8080/jalgo/binance",
  ping: async () => {
    return await api.get(path + "/ping");
  },
};
export default binance;
