const objectUtils = {
  convertValueToObject: (field, value) => {
    return {
      [field]: value,
    };
  },
  getType: (object) => {
    return typeof object;
  },
  resolve: (obj, path) => {
    return path.split(".").reduce(
      function (prev, curr) {
        return prev ? prev[curr] : null;
      },
      obj ? obj : {} || this.self
    );
  },
  isEmpty: (object) => {
    return this.isUndefined(object) || this.isNull(object) ? true : false;
  },
  hasValue: (object) => {
    return !objectUtils.isUndefined(object) && !objectUtils.isNull(object);
  },
  isString: (value) => {
    return typeof value === "string" || value instanceof String;
  },
  isNumber: (value) => {
    return typeof value === "number" && isFinite(value);
  },
  isArray: (value) => {
    return value && typeof value === "object" && value.constructor === Array;
  },
  isFunction: (value) => {
    return typeof value === "function";
  },
  isObject: (value) => {
    return value && typeof value === "object" && value.constructor === Object;
  },
  isNull: (value) => {
    return value === null;
  },
  isUndefined: (value) => {
    return typeof value === "undefined" || value === "undefined";
  },
  isBoolean: (value) => {
    return typeof value === "boolean";
  },
  isRegExp: (value) => {
    return value && typeof value === "object" && value.constructor === RegExp;
  },
  isDate: (value) => {
    return value instanceof Date;
  },
};
export default objectUtils;
