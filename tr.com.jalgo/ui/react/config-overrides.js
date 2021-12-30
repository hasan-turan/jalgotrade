const { alias, configPaths } = require("react-app-rewire-alias");
const path = require("path");
module.exports = function override(config, env) {
  alias({
    "@api": path.join(__dirname, "/src/api/"),
    "@lib": path.join(__dirname, "/src/lib/"),
    "@utils": path.join(__dirname, "/src/lib/utils/"),
    "@css": path.join(__dirname, "/src/assets/css/"),
  })(config);
  
  return config;
};
