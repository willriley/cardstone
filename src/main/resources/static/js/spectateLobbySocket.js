const SPECTATE_MESSAGE_TYPE = {
	OPPONENT_ENTERED_LOBBY: 2,
	OPPONENT_LEFT_LOBBY: 3,
	GAME_IS_STARTING: 7,
	LOBBY_CANCELLED: 8,
  SPECTATOR_CONNECT: 12,
  SPECTATEE_UPDATE: 13
};

class SpectateLobbySocket {

  constructor(userId, onOpponentJoin, onOpponentLeave, onGameStart, onLobbyCancel) {
    this.websocket = new WebSocket("ws://" + window.location.host + "/lobbySocket");
    this.websocket.server = this;
		this.websocket.socket = this.websocket;
		this.websocket.onmessage = this.onWebSocketMessage;
		this.websocket.onopen = this.onWebSocketOpen;
    this.onOpponentJoin = onOpponentJoin;
		this.onOpponentLeave = onOpponentLeave;
    this.onGameStart = onGameStart;
		this.onLobbyCancel = onLobbyCancel;
  }

  onWebSocketOpen() {
		const type = SPECTATE_MESSAGE_TYPE.SPECTATOR_CONNECT;
		const obj = {"type": type, "payload": payload};
	  this.socket.send(JSON.stringify(obj));
		console.log('opened');
	}

	onWebSocketMessage(event) {
		console.log(event.data)
		this.server.messageReceived(JSON.parse(event.data));
	}

  updateSpectatee(id) {
    const obj = {"type": SPECTATE_MESSAGE_TYPE.SPECTATEE_UPDATE, "payload": {"id": id}};
    this.websocket.send(JSON.stringify(obj));
    console.log("sent update: " + obj);
  }

  messageReceived(message) {
    switch(message.type) {
      case SPECTATE_MESSAGE_TYPE.OPPONENT_ENTERED_LOBBY:
        this.onOpponentJoin(message.payload.id);
        break;
      case SPECTATE_MESSAGE_TYPE.OPPONENT_LEFT_LOBBY:
        this.onOpponentLeave();
        break;
      case SPECTATE_MESSAGE_TYPE.GAME_IS_STARTING:
        this.onGameStart();
        break;
      case SPECTATE_MESSAGE_TYPE.LOBBY_CANCELLED:
        this.onLobbyCancel();
        break;
    }
  }

}