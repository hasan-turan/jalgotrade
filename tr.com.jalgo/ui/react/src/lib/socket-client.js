import logger from "./logger";

class SocketClient {
  constructor(baseUrl, path, handler) {
    this.baseUrl = baseUrl;
    this.socketPath = path;
    this.handler = handler;
    this.webSocket = null;
    this.createSocket();
  }

  createSocket() {
    const wsUrl = `${this.baseUrl}${this.socketPath}`;
    console.log(`URL:${wsUrl} `);
    this.webSocket = new WebSocket(wsUrl);

    this.webSocket.onopen = () => {
      logger.info(`Ws:Connected to ${wsUrl}`);
    };

    this.webSocket.onclose = () => {
      logger.info(`Ws:Disconnected from ${wsUrl}`);
    };

    this.webSocket.onerror = (err) => {
      logger.warn(`Ws:Error ${wsUrl} ->${err}`);
    };

    this.webSocket.onmessage = (msg) => {
      try {
        const message = JSON.parse(msg.content);
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
    setInterval(() => {
      if (this.webSocket.readyState === WebSocket.OPEN) {
        if (this.pingFn) this.pingFn(this.listenKey);
        logger.debug(`Ping sent for key:${this.listenKey}`);
      }
    }, 25 * 60 * 1000); // ping every 30 minutes
  }

   

  closeSocket(listenKey) {
    if (this.webSocket) {
      this.webSocket.close();
    }
  }
}

export default SocketClient;
