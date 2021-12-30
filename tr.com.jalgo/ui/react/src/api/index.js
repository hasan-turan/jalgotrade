import notificationUtils from  "../lib/utils/notification-utils";

import objectUtils from  "@utils/object-utils"
import stringUtils from  "@utils/string-utils"
import localStorageUtils from  "@utils/local-storage-utils"
const serverUrl = "http://localhost:8080/";
 
const api = {
  get: async (url, params = {}) => {
    //stopPopuptimer();
    if (localStorageUtils.getAuthToken() === null) {
      notificationUtils.warn("İşlemi gerçekleştirmek için önce oturum açmalısınız");
      return;
    }

    
    const reqUrl= serverUrl + url;

    const header = new Headers({
      "Content-Type": "application/json; charset=utf-8",
      Accept: "application/json",
      //Authorization: LocalStorageUtils.getAuthorization(),
      //"X-AUTH-TOKEN": LocalStorageUtils.getAuthToken(),
    });
    const data = await fetch(reqUrl, {
      method: "GET",
      headers: header,
    })
      .then((response) => {
        if (params.log) console.log("get response json", response);
        return response.json();
      })
      .then((response) => {
        if (params.log) console.log("get response ", response);
        if (response.status === "OK") {
          if (!stringUtils.isNullUndefinedOrWhiteSpace(response.message))
            notificationUtils.info(response.message);
          return response.data;
        }

        notificationUtils.warn(response.message);
      })
      .catch((error) => {
        notificationUtils.error("Get Error " + error);
      })
      .finally(() => {
        //startPopuptimer();
      });

    return data;
  },
  post: async (
    url,
    params = {
      data: {},
      showMessage: false,
      headers: {},
      log: false,
    }
  ) => {
    //let csrfToken = Cookies.get("XSRF-TOKEN");

    let reqUrl = serverUrl + url;

    // prettier-ignore
    const headers = new Headers({
          "Content-Type": "application/json; charset=utf-8",
          'Accept': "application/json",
         // 'X-XSRF-TOKEN': csrfToken,
        //  'X-AUTH-TOKEN': LocalStorageUtils.getAuthToken(),
      
        });

    let body = {};
    if (params && !objectUtils.isUndefined(params.data))
      body = JSON.stringify(params.data);

    const data = await fetch(reqUrl, {
      method: "POST",
      headers: headers,
      body: body,
      credentials: "include",
    })
      .then((response) => {
        if (params.log) console.log("post response json", response);

        //setJwtToken(response);

        return response.json();
      })
      .then((response) => {
        if (params.log) console.log("post response", response);

        if (response.status === "OK") {
          if (params.showMessage)
            notificationUtils.info(response.message || "İşlem başarıyla gerçekleştirildi");

          return response.data;
        }

        notificationUtils.warn(response.message);
      })
      .catch((error) => {
        notificationUtils.error(error);
        throw error;
      })
      .finally(() => {});

    return data;
  },
};
export default api;