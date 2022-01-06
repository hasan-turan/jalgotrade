import React, { useEffect, useRef } from "react";
import UpdateKLineChart from "../charts/kline/update-kline-chart";
//Ctrl + Click (For goto definition install "Smart JS GoTo" extension otherwise goto definition with aliases wont work )
import binance from "@api/binance";
 
import { Button, Rate, Row, Col, Space, Tabs, Input, Spin } from "antd";
import notificationUtils from "@lib/utils/notification-utils";
import { useState } from "react/cjs/react.development";
 
import { SearchOutlined } from "@ant-design/icons";

 
 
import "@assets/css/kline-chart.css";
import "@assets/css/kline-chart.less";
import { ParityType } from "@lib/types/parity-types";


const Binance = () => {
  const parityGroup = [ParityType.USDT, ParityType.BTC, ParityType.ETH];

  const parity = "btcusdt";
  const interval = "4h";
  const environment = "live";
  const [klineSocket, setKlineSocket] = useState(null);
  const [exchangeInfo, setExchangeInfo] = useState(null);
  const [exchangeInfoLoading, setExchangeInfoLoading] = useState(true);
  const [parities, setParities] = useState([]);

  useEffect(() => {
    getExchangeInfo();
  }, []);
  const getExchangeInfo = () => {
    binance.exchangeInfo().then((data) => {
      var exInfo = JSON.parse(data);
      // exInfo.symbol = objectArrayUtils.sort(exInfo.symbols, "symbol");
      setExchangeInfoLoading(false);
      setExchangeInfo(exInfo);
      setParities([
        {
          name: ParityType.USDT,  
          items: getParityPairs(ParityType.USDT, exInfo.symbols),
        },
        {
          name: ParityType.BTC,
          items: getParityPairs(ParityType.BTC, exInfo.symbols),
        },
        {
          name: ParityType.ETH,
          items: getParityPairs(ParityType.ETH, exInfo.symbols),
        },
        {
          name: ParityType.OTHERS,
          items: getParityPairs(ParityType.OTHERS, exInfo.symbols),
        },
      ]);
    });
  };
  const onConnectToWebsocket = () => {
    binance
      .kline(parity, interval, environment, (message) => {
        console.log(message);
      })
      .then((socket) => {
        setKlineSocket(socket);
      });
  };

  const onDisconnectFromWebsocket = () => {
    if (klineSocket) {
      klineSocket.closeSocket();
      notificationUtils.success("Kline web socket closed!");
    }
  };
  const onFilterSymbols = (e, parity) => {
    var others = parities.filter((p) => p.name != parity);
    setParities([
      ...others,
      {
        name: parity,
        items: filterParityPairs(e.target.value, parity, exchangeInfo.symbols),
      },
    ]);
  };

  const filterParityPairs = (filterText, parity, list) => {
    return getParityPairs(parity, exchangeInfo.symbols).filter((s) =>
      filterText.length > 0
        ? s.symbol.toLowerCase().indexOf(filterText.toLowerCase()) > -1
        : true
    );
  };
  const getParityPairs = (parity, list) => {
    return list.filter((s) =>
      parity != ParityType.OTHERS
        ? s.quoteAsset == parity
        : !parityGroup.includes(s.quoteAsset)
    );
  };

  const generateParityPairItems = (parityName) => {
    if (!parities || parities.length == 0) return;

    var parity = parities.find((p) => p.name == parityName);

    var parityItems =
      parity.items &&
      parity.items.map((symbol) => {
        return (
          <Row key={"row-" + symbol.symbol} symbol={symbol}>
            <Col span={10}>
              <Space>
                <Rate
                  key={symbol.symbol}
                  className="x-rate"
                  defaultValue={0}
                  allowClear={true}
                  count={1}
                />
                {symbol.symbol}
              </Space>
            </Col>
          </Row>
        );
      });

    return (
      <React.Fragment>
        <Input
          size="small"
          onChange={(e) => onFilterSymbols(e, parityName)}
          prefix={<SearchOutlined />}
        />
        <div className="x-parity-container">{parityItems}</div>
        <div className="x-parity-total">
          {" "}
          Total: {parity.items ? parity.items.length : 0}
        </div>
      </React.Fragment>
    );
  };

  return (
    <React.Fragment>
      <Space>
        <Button onClick={onConnectToWebsocket}>Connect to server</Button>
        <Button onClick={onDisconnectFromWebsocket}>
          Disconnect from server
        </Button>
      </Space>
      <Row>
        <Col span={6}>
          <Spin tip="Loading" spinning={exchangeInfoLoading}>
            <Tabs className="x-tabs" defaultActiveKey="USDT">
              <Tabs.TabPane tab="USDT" key="USDT">
                {generateParityPairItems(ParityType.USDT)}
              </Tabs.TabPane>
              <Tabs.TabPane tab="BTC" key="BTC">
                {generateParityPairItems(ParityType.BTC)}
              </Tabs.TabPane>
              <Tabs.TabPane tab="ETH" key="ETH">
                {generateParityPairItems(ParityType.ETH)}
              </Tabs.TabPane>
              <Tabs.TabPane tab="OTHERS" key="OTHERS">
                {generateParityPairItems(ParityType.OTHERS)}
              </Tabs.TabPane>
            </Tabs>
          </Spin>
        </Col>
        <Col span={18}>
          <div className="app">
            <UpdateKLineChart />
          </div>
        </Col>
      </Row>
    </React.Fragment>
  );
};

export default Binance;
