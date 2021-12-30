const environmentUtils = {
  isDevelopment: () => {
    return process.env.NODE_ENV === "development";
  },

  isProduction: () => {
    return process.env.NODE_ENV === "production";
  },
};

export default environmentUtils;
