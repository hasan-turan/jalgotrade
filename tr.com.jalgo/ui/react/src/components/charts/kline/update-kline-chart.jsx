import React, { useEffect } from "react";
import { init, dispose } from "klinecharts";

import Layout from "./layout";
import generatedKlineDatalist from "@lib/utils/generated-kline-datalist";


const UpdateKLineChart = () => {
  function updateData(kLineChart) {
    setTimeout(() => {
      if (kLineChart) {
        const dataList = kLineChart.getDataList();
        const lastData = dataList[dataList.length - 1];
        const newData = generatedKlineDatalist(
          lastData.timestamp,
          lastData.close,
          1
        )[0];
        newData.timestamp += 1000 * 60;
        kLineChart.updateData(newData);
      }
      updateData(kLineChart);
    }, 1000);
  }

  useEffect(() => {
    const kLineChart = init("update-k-line");
    kLineChart.applyNewData(generatedKlineDatalist());
    console.log("kLineChart",kLineChart);
    updateData(kLineChart);
    return () => {
      dispose("update-k-line");
    };
  }, []);
  return (
    <Layout title="Real-time update">
      <div id="update-k-line" className="k-line-chart" />
    </Layout>
  );
};

export default UpdateKLineChart;