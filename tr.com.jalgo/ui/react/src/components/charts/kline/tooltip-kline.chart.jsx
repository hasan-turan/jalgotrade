import React, { PureComponent } from "react";
import { init, dispose } from "klinecharts";
 
import Layout from "./layout";
import generatedKlineDatalist from "@lib/utils/generated-kline-datalist";
function getTooltipOptions(
  candleShowType,
  candleShowRule,
  technicalIndicatorShowRule
) {
  return {
    candle: {
      tooltip: {
        showType: candleShowType,
        showRule: candleShowRule,
        labels: ["Opening price:", "Closing price:", "Rise and fall:"],
        values: (kLineData) => {
          const change =
            ((kLineData.close - kLineData.open) / kLineData.open) * 100;
          return [
            { value: kLineData.open.toFixed(2) },
            { value: kLineData.close.toFixed(2) },
            {
              value: `${change.toFixed(2)}%`,
              color: change < 0 ? "#EF5350" : "#26A69A",
            },
          ];
        },
      },
    },
    technicalIndicator: {
      tooltip: {
        showRule: technicalIndicatorShowRule,
      },
    },
  };
}

const rules = [
  { key: "always", text: "Always display" },
  { key: "follow_cross", text: "Follow the cross cursor" },
  { key: "none", text: "not displayed" },
];

export default class TooltipKLineChart extends PureComponent {
  state = {
    candleShowType: "standard",
    candleShowRule: "always",
    technicalIndicatorShowRule: "always",
  };

  componentDidMount() {
    const { candleShowType, candleShowRule, technicalIndicatorShowRule } =
      this.state;
    this.kLineChart = init("tooltip-k-line");
    this.kLineChart.createTechnicalIndicator("MA", false, {
      id: "candle_pane",
    });
    this.kLineChart.createTechnicalIndicator("KDJ", false, { height: 80 });
    this.kLineChart.setStyleOptions(
      getTooltipOptions(
        candleShowType,
        candleShowRule,
        technicalIndicatorShowRule
      )
    );
    this.kLineChart.applyNewData(generatedKlineDatalist());
  }

  componentDidUpdate(prevProps, prevState, snapshot) {
    const { candleShowType, candleShowRule, technicalIndicatorShowRule } =
      this.state;
    if (
      prevState.candleShowType !== candleShowType ||
      prevState.candleShowRule !== candleShowRule ||
      prevState.technicalIndicatorShowRule !== technicalIndicatorShowRule
    ) {
      this.kLineChart.setStyleOptions(
        getTooltipOptions(
          candleShowType,
          candleShowRule,
          technicalIndicatorShowRule
        )
      );
    }
  }

  componentWillUnmount() {
    dispose("tooltip-k-line");
  }

  render() {
    return (
      <Layout title="Cross cursor text prompt">
        <div id="tooltip-k-line" className="k-line-chart" />
        <div className="k-line-chart-menu-container">
          <span style={{ paddingRight: 10 }}> Main image display type </span>
          <button
            onClick={(_) => {
              this.setState({
                candleShowType: "standard",
              });
            }}
          >
            default
          </button>
          <button
            onClick={(_) => {
              this.setState({
                candleShowType: "rect",
              });
            }}
          >
            Rectangle
          </button>
        </div>
        <div className="k-line-chart-menu-container">
          <span style={{ paddingRight: 10 }}>
            {" "}
            k-line prompt display rules{" "}
          </span>
          {rules.map(({ key, text }) => {
            return (
              <button
                key={key}
                onClick={(_) => {
                  this.setState({
                    candleShowRule: key,
                  });
                }}
              >
                {text}
              </button>
            );
          })}
        </div>
        <div className="k-line-chart-menu-container">
          <span style={{ paddingRight: 10 }}>
            {" "}
            Indicator prompt display rules{" "}
          </span>
          {rules.map(({ key, text }) => {
            return (
              <button
                key={key}
                onClick={(_) => {
                  this.setState({
                    technicalIndicatorShowRule: key,
                  });
                }}
              >
                {text}
              </button>
            );
          })}
        </div>
      </Layout>
    );
  }
}
