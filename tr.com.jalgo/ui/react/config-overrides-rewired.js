const { alias } = require("react-app-rewire-alias");
const rewireLess = require("react-app-rewire-less");
const path = require("path");
module.exports = function override(config, env) {
  config = rewireLess.withLoaderOptions({
    javascriptEnabled: true,
  })(config, env);

  alias({
    "@api": path.join(__dirname, "/src/api/"),
    "@lib": path.join(__dirname, "/src/lib/"),
    "@assets": path.join(__dirname, "/src/assets/"),
    "@components": path.join(__dirname, "/src/components/"),
  })(config);

  //also run command for typescript--> npm install --save typescript @types/node @types/react @types/react-dom @types/jest
  config.resolve.extensions = [
    ...config.resolve.extensions,
    ".less",
    ".ts",
    ".tsx",
  ];

  return config;
};
