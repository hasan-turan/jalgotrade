import objectUtils from "./object-utils";
 

const stringUtils = {
  
  htmlDecode: (message) => {
    const result = Array.isArray(message) ? message.join(" <br> ") : message;
    return <div> {result}</div>;
  },
  endsWith: (str, suffix) => {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
  },
  trim: (str) => {
    return str.trim();
  },
  isNullUndefinedOrWhiteSpace: (str) => {
    if (objectUtils.isNull(str) || objectUtils.isUndefined(str)) return true;

    if (!objectUtils.isString(str))
      throw new Error("Object type is not a string");

    return stringUtils.trim(str).length === 0;
  },
};
export default stringUtils;
