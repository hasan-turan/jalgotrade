import React, { useState } from "react";
import { Breadcrumb, Layout, Menu } from "antd";
import "@assets/css/app.css";
import "@assets/css/app.less";
import {
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UserOutlined,
  VideoCameraOutlined,
  UploadOutlined,
  MenuOutlined,
} from "@ant-design/icons";

import { Routes, Route } from "react-router-dom";
import About from "./about";
import Home from "./home";
import Binance from "./binance";

import { useNavigate } from "react-router-dom";

const { Header, Footer, Sider, Content } = Layout;
const { SubMenu } = Menu;

const App = (props) => {
  const navigate = useNavigate();
  const [collapsed, setCollapsed] = useState(false);
  const navigateToPage = (path) => {
    console.log("Path", path);
    navigate(path);
  };

  const onCollapse = () => {
    setCollapsed(!collapsed);
  };

  return (
    <Layout className="x-main-layout">
      <Header className="x-site-header" style={{ padding: 0 }}>
        Header
      </Header>

      <Layout className="x-site-layout">
        <Sider
          className="x-sider"
          collapsible
          collapsed={collapsed}
          onCollapse={onCollapse}
        >
          <div className="x-logo" />
          <Menu mode="inline" defaultSelectedKeys={["1"]}>
            <SubMenu
              key="menuExchanges"
              icon={<MenuOutlined />}
              title="Exchanges"
            >
              <Menu.Item
                key="menuBinance"
                onClick={() => navigateToPage("/binance")}
              >
                Binance
              </Menu.Item>
            </SubMenu>
          </Menu>
        </Sider>
        <Layout className="x-site-content-layout">
          <Content className="x-site-content">
            <Routes>
              <Route exact path="/" element={<Home />} />
              <Route exact path="/binance" element={<Binance />} />
              <Route exact path="/about" element={<About />} />
            </Routes>
          </Content>
        </Layout>
      </Layout>
      <Footer className="x-site-footer">Footer</Footer>
    </Layout>
  );
};

export default App;
