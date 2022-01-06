import logger from "./logger";

class SocketClient {
  constructor(url, handler) {
    this.url = url;
   
    this.handler = handler;
    this.webSocket = null;
    this.createSocket();
  }

  createSocket() {
    
    this.webSocket = new WebSocket(this.url);
  
    this.webSocket.onopen = () => {
      logger.info(`Ws:Connected to ${this.url}`);
    };

    this.webSocket.onclose = () => {
      logger.info(`Ws:Disconnected from ${this.url}`);
    };

    this.webSocket.onerror = (err) => {
      logger.warn(`Ws:Error ${this.url} ->${err}`);
    };

    this.webSocket.onmessage = (message) => {
      try {
        if (this.handler) this.handler(message);
      } catch (ex) {
        logger.err("Parse message failed", ex);
      }
    };
  }

  /**
   * Keepalive a user data stream to prevent a time out.
   * User data streams will close after 60 minutes.
   * It's recommended to send a ping about every 30 minutes. */
  heartBeat() {
    // setInterval(() => {
    //   if (this.webSocket.readyState === WebSocket.OPEN) {
    //     if (this.pingFn) this.pingFn(this.listenKey);
    //     logger.debug(`Ping sent for key:${this.listenKey}`);
    //   }
    // }, 25 * 60 * 1000); // ping every 30 minutes
  }

   

  closeSocket(listenKey) {
    if (this.webSocket) {
      this.webSocket.close();
    }
  }
}

export default SocketClient;
