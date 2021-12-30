import objectUtils from "./object-utils";

const keys = {
  authToken: "_auth_token_",
};

const localStorageUtils = {
  set: (key, data) => {
    if (objectUtils.isObject(data) || objectUtils.isArray(data)) {
      localStorage.setItem(key, JSON.stringify(data));
    } else {
      localStorage.setItem(key, data);
    }
  },
  get: (key, defaultValue) => {
    const data = localStorage.getItem(key);

    try {
      return objectUtils.hasValue(data) ? JSON.parse(data) : defaultValue;
    } catch {
      return objectUtils.hasValue(data) ? data : defaultValue;
    }
  },

  getAuthToken: () => {
    return localStorageUtils.get(keys.authToken, null);
  },

  setAuthToken: (data) => {
    return localStorageUtils.set(keys.authToken, data);
  },
};
export default localStorageUtils;
