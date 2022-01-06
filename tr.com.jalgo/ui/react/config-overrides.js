const { override, addWebpackAlias, addLessLoader } = require("customize-cra");
const path = require("path");
 
module.exports = override(
  addLessLoader(),
  addWebpackAlias({
    "@": path.resolve(__dirname, "./src/"),
    "@api": path.join(__dirname, "/src/api/"),
    "@lib": path.join(__dirname, "/src/lib/"),
    "@assets": path.join(__dirname, "/src/assets/"),
    "@components": path.join(__dirname, "/src/components/"),
  }),
  (config) => {
    config.resolve.extensions = [
      ...config.resolve.extensions,
      ".less",
      ".ts",
      ".tsx",
    ];
    return config;
  }
);
