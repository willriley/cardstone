package game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import logins.Db;
import server.CommsWebSocket;

/**
 * Class to keep track of all games currently running and handle playing them.
 * Can handle an arbitrary amount of games at once. Also stashes replay info.
 * @author Raghu and Willayyy
 */
public class GameManager {
  private static final Gson GSON = new Gson();
  private static GamePool games = new GamePool();
  private static Map<Integer, Integer> gamesToEventNums = new ConcurrentHashMap<>();

  // some sort of method to add games.
  public static void addGame(Game game) {
    if (games.updateGame(game)) {
      gamesToEventNums.put(game.getId(), 1);
    }
  }

  // remove games when they complete.
  public static void endGame(GameStats ended) {
    Game g = ended.getGame();
    int gId = g.getId();
    int winner = ended.getWinnerId();
    int turns = ended.getNumTurns();

    int firstUser = g.getActivePlayerId();
    int secondUser = g.getOpposingPlayerId(firstUser);

    try {
      Db.update("delete from in_progress where id = ?;", gId);
      Db.update("insert into finished_game values(?, ?, ?)", gId, winner,
          turns);
      Db.update("insert into user_game values(?, ?);", firstUser, gId);
      Db.update("insert into user_game values(?, ?);", secondUser, gId);

      games.removeGame(firstUser);
      games.removeGame(secondUser);
      gamesToEventNums.remove(gId);
    } catch (SQLException | NullPointerException e) {
      throw new RuntimeException();
    }
  }

  public static boolean playerIsInGame(int playerId) {
    return games.getGameByPlayerId(playerId) != null;
  }

  public static Game getGameByPlayerId(int playerId) {
    return games.getGameByPlayerId(playerId);
  }

  public static void receiveUnderstoodBoardState(int playerId,
      JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      // game.h
    }
  }

  public static void receiveTargetedCard(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handleCardTargeted(message, playerId);
    }
  }

  public static void receiveTargetedPlayer(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handlePlayerTargeted(message, playerId);
    }
  }

  public static void receiveAttemptedToPlay(int playerId, JsonObject message) {
    System.out.println("got attempt to play.");
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handleCardPlayed(message, playerId);
    }
  }

  public static void receiveChooseResponse(int playerId, JsonObject message) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handleChosen(message, playerId);
    }
  }

  public static void receiveTurnEnd(int playerId) {
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      game.handleTurnend(null, playerId);
      System.out.println("handled turn end.");
    }
  }

  public static void receivePlayerChat(int playerId, JsonObject message) {
    String chat = message.get("message").getAsString();
    Game game = games.getGameByPlayerId(playerId);
    if (game != null) {
      int idToSend = game.getOpposingPlayerId(playerId);
      try {
        CommsWebSocket.sendChatMessage(idToSend, chat);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void playerIsReady(int uId) {
    try {
      Game game = games.getGameByPlayerId(uId);
      CommsWebSocket.sendWholeBoardSate(game, uId);
      CommsWebSocket.sendTurnStart(uId, game.isActivePlayer(uId));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Caches board states and inserts their JSON into the Db for replay purposes.
   * @param g the game
   */
  public static void pushToDb(Game g) {
    // add game to cache
    if (games.updateGame(g)) {
      String eventInsert = "insert into game_event values(?, ?, ?);";
      int gId = g.getId();
      int eventNum = gamesToEventNums.get(gId);
      // insert JSON board state into Db for replay purposes
      // and increment the event number
      try {
        Db.update(eventInsert, gId, eventNum, g.jsonifySelf());
      } catch (SQLException | NullPointerException e) {
        e.printStackTrace();
      }
      gamesToEventNums.put(gId, eventNum++);
    }
  }

  /**
   * Returns the board state for a game event as a JsonObject.
   * @param gameId the game id.
   * @param eventNum the event number
   * @return a JsonObject of gameId's board state at eventNum
   */
  public static JsonObject boardFrom(int gameId, int eventNum) {
    String eventQuery = "select board from game_event where "
        + "game = ? and event = ?;";
    try (ResultSet rs = Db.query(eventQuery, gameId, eventNum)) {
      rs.next();
      String board = rs.getString(1);
      assert !rs.next();
      return GSON.fromJson(board, JsonObject.class);
    } catch (SQLException | NullPointerException e) {
      return null;
    }
  }
}
