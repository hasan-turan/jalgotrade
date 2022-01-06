import { message } from "antd";

const notificationUtils = {
  success: (text) => {
    message.success(text);
  },
  warn: (text) => {
    message.warning(text);
  },
  error: (text) => {
    message.error(text);
  },
};
export default notificationUtils;
