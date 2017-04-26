package game;

import com.google.gson.JsonObject;

/**
 * Class to keep track of all games currently running and handle playing them.
 *
 * @author Raghu
 *
 */
public class GameManager {
  private static GamePool games = new GamePool();

  // some sort of method to add games.
  public static void addGame() {

  }

  // remove games when they complete.
  public static void removeGame(Game game) {

  }

  public static boolean playerIsInGame(int playerId) {
    return false;
  }

  public static Game getGameByPlayerId(int playerId) {
    return null;
  }

  public static Game getGameById(int gameId) {
    return null;
  }

  public static void receiveUnderstoodBoardState(int playerId,
      JsonObject message) {

  }

  public static void recieveTargetedCard(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handleCardTargeted(message, playerId);
    }
  }

  public static void recieveTargetedPlayer(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handlePlayerTargeted(message, playerId);
    }
  }

  public static void receiveAttemptedToPlay(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handleCardPlayed(message, playerId);
    }
  }

  public static void receiveChooseResponse(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      // game.
    }
  }

  // create a different method for each message I can receive (see
  // messageTypeEnum). They should all take some playerId and
  // a JSON object (the payload).
}
