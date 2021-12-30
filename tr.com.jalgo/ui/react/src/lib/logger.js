const logger = {
  debug: (...arg) => {
    console.log("", "DEBUG", ...arg);
  },
  info: (...arg) => {
    console.log("", "INFO", ...arg);
  },
  infox: (...arg) => {
    console.log("xxx", "INFO", ...arg);
  },
  warn: (...arg) => {
    console.log("", "WARN", ...arg);
  },
  error: (...arg) => {
    console.log("", "ERROR", ...arg);
  },
};

export default logger;
