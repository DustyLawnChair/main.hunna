package com.PlayTrackerWebApp.playtracker.dao;

import com.PlayTrackerWebApp.playtracker.model.Leagues;
import com.PlayTrackerWebApp.playtracker.model.Teams;
import com.PlayTrackerWebApp.playtracker.model.players.NBAplayer;
import com.PlayTrackerWebApp.playtracker.model.players.Player;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
public class StatsDaoImpl implements StatsDao {

    private static final String selectAllPlayers
            = "SELECT * FROM nbaplayerstable WHERE isDeleted = FALSE";

    private static final String selectAllTeams
            = "SELECT distinct teamName, teamLocation, teamConf, teamDiv, teamWins, teamLosses\n" +
            "FROM nbaplayerstable\n" +
            "ORDER BY teamName";

    private static final String selectPlayersByTeam =
            "SELECT playerId, playerNumber, playerName, playerPosition, teamName, teamLocation, " +
                    "       teamWins, teamLosses, teamConf, teamDiv, points, assists, rebounds, steals, blocks, gamesPlayed " +
                    "FROM nbaplayerstable " +
                    "WHERE teamName = ? AND isDeleted = FALSE";

    private static String selectPlayersByName =
            "SELECT playerId, playerNumber, playerName, playerPosition, teamName, " +
                    "teamLocation, teamWins, teamLosses, teamConf, teamDiv, " +
                    "points, assists, rebounds, steals, blocks, gamesPlayed, isDeleted " +
                    "FROM nbaplayerstable " +
                    "WHERE playerName LIKE ? AND isDeleted = FALSE";

    private static final String selectPlayersById
            = "SELECT * FROM NBAplayersTable WHERE playerId = ? AND isDeleted = FALSE";

    private static final String selectPlayerStats =
            "SELECT playerName, playerNumber, playerPosition, teamName, gamesPlayed, points, rebounds, assists, steals, blocks " +
                    "FROM NBAplayersTable " +
                    "WHERE playerId = ? AND isDeleted = FALSE";
    private static String insertNBAplayer = "INSERT INTO NBAplayersTable (playerId, playerNumber, playerName, playerPosition, " +
            "isDeleted, teamName, teamLocation, teamWins, teamLosses, teamConf, teamDiv, " +
            "points, assists, rebounds, steals, blocks, gamesPlayed) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String updatePlayerById = "UPDATE NBAplayersTable " +
            "SET playerName = ?, playerNumber = ?, playerPosition = ?, teamName = ?, " +
            "    teamLocation = ?, teamWins = ?, teamLosses = ?, teamConf = ?, teamDiv = ?, " +
            "    points = ?, assists = ?, rebounds = ?, steals = ?, blocks = ?, gamesPlayed = ? " +
            "WHERE playerId = ? AND isDeleted = FALSE";

    private static final String softDeletePlayersById =
            "UPDATE NBAplayersTable " +
                    "SET isDeleted = TRUE " +
                    "WHERE playerId = ? AND isDeleted = FALSE";

    private static final String restoreAllDeletedPlayers =
            "UPDATE NBAplayersTable " +
                    "SET isDeleted = FALSE " +
                    "WHERE isDeleted = TRUE";




    @Override
    public List<NBAplayer> getPlayers() {
        List<NBAplayer> myNBAplayers = new ArrayList<>();
        ResultSet result = null;
        Statement statement = null;
        Connection conn = MariaDbUtil.getConnection();

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(selectAllPlayers);
            myNBAplayers = makeNBAplayer(result); // Pass connection to fetch associated team

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myNBAplayers;
    }

    @Override
    public List<NBAplayer> getTeams() {
        List<NBAplayer> myNBAteams = new ArrayList<>();
        ResultSet result = null;
        Statement statement = null;
        Connection conn = MariaDbUtil.getConnection();

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(selectAllTeams);
            myNBAteams = makeNBAteam(result); // Pass connection to fetch associated team

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myNBAteams;
    }


    public List<NBAplayer> getPlayersByTeam(String teamName) {
        List<NBAplayer> players = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement statement = null;
        Connection conn = null;

        try {
            conn = MariaDbUtil.getConnection();

            statement = conn.prepareStatement(selectPlayersByTeam);
            statement.setString(1, teamName);

            result = statement.executeQuery();

            while (result.next()) {
                NBAplayer player = new NBAplayer();
                player.setPlayerId(result.getInt("playerId"));
                player.setPlayerNumber(result.getInt("playerNumber"));
                player.setPlayerName(result.getString("playerName"));
                player.setPlayerPosition(result.getString("playerPosition"));
                player.setTeamName(result.getString("teamName"));
                player.setTeamLocation(result.getString("teamLocation"));
                player.setTeamWins(result.getInt("teamWins"));
                player.setTeamLosses(result.getInt("teamLosses"));
                player.setTeamConf(result.getString("teamConf"));
                player.setTeamDiv(result.getString("teamDiv"));
                player.setPoints(result.getDouble("points"));
                player.setAssists(result.getDouble("assists"));
                player.setRebounds(result.getDouble("rebounds"));
                player.setSteals(result.getDouble("steals"));
                player.setBlocks(result.getDouble("blocks"));
                player.setGamesPlayed(result.getInt("gamesPlayed"));

                System.out.println("Executing SQL query: " + selectPlayersByTeam);
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return players;
    }
    @Override
    public List<NBAplayer> getPlayerById(int playerId) {
        List<NBAplayer> myNBAplayer = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement ps = null;
        Connection conn = MariaDbUtil.getConnection();

        try {
            ps = conn.prepareStatement(selectPlayersById);
            ps.setInt(1, playerId);
            result = ps.executeQuery();
            myNBAplayer = makeNBAplayer(result);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(result != null) result.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myNBAplayer;
    }

    @Override
    public List<NBAplayer> getPlayersByName(@RequestParam String query) {
        List<NBAplayer> myNBAplayers = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement ps = null;
        Connection conn = MariaDbUtil.getConnection();

        try {

            ps = conn.prepareStatement(selectPlayersByName);
            String nameValue = "%" + query + "%";
            ps.setString(1, nameValue);
            result = ps.executeQuery();
            myNBAplayers = makeNBAplayer(result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return myNBAplayers;
    }

    @Override
    public List<NBAplayer> getPlayerStats(int playerId) {
        List<NBAplayer> playerStats = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement ps = null;
        Connection conn = MariaDbUtil.getConnection();

        try {
            ps = conn.prepareStatement(selectPlayerStats);
            ps.setInt(1, playerId);

            System.out.println("Executing SQL: " + ps.toString());

            result = ps.executeQuery();

            if (result.next()) {
                playerStats = makeNBAplayer(result);
                System.out.println("Retrieved player stats: " + playerStats);
            } else {
                System.out.println("No player stats found for playerId: " + playerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (ps != null) ps.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return playerStats;
    }

    /*
    @Override
    public List<NBAplayer> getPlayersByFancySearch(Boolean guard, Boolean forward, Boolean center,
                                                   String conference, Double points, Double assists,
                                                   Double rebounds, Double steals, Double blocks) {
        List<NBAplayer> players = new ArrayList<>();
        ResultSet result = null;
        PreparedStatement ps = null;
        Connection conn = MariaDbUtil.getConnection();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM NBAplayersTable WHERE isDeleted = FALSE");

        if (guard != null && guard) {
            queryBuilder.append(" AND playerPosition = 'Guard'");
        }
        if (forward != null && forward) {
            queryBuilder.append(" AND playerPosition = 'Forward'");
        }
        if (center != null && center) {
            queryBuilder.append(" AND playerPosition = 'Center'");
        }
        if (conference != null && !conference.isEmpty()) {
            queryBuilder.append(" AND teamConf = ?");
        }
        if (points != null) {
            queryBuilder.append(" AND points >= ?");
        }
        if (assists != null) {
            queryBuilder.append(" AND assists >= ?");
        }
        if (rebounds != null) {
            queryBuilder.append(" AND rebounds >= ?");
        }
        if (steals != null) {
            queryBuilder.append(" AND steals >= ?");
        }
        if (blocks != null) {
            queryBuilder.append(" AND blocks >= ?");
        }

        try {
            ps = conn.prepareStatement(queryBuilder.toString());

            int paramIndex = 1;

            if (conference != null && !conference.isEmpty()) {
                ps.setString(paramIndex++, conference);
            }
            if (points != null) {
                ps.setDouble(paramIndex++, points);
            }
            if (assists != null) {
                ps.setDouble(paramIndex++, assists);
            }
            if (rebounds != null) {
                ps.setDouble(paramIndex++, rebounds);
            }
            if (steals != null) {
                ps.setDouble(paramIndex++, steals);
            }
            if (blocks != null) {
                ps.setDouble(paramIndex++, blocks);
            }

            result = ps.executeQuery();

            while (result.next()) {
                NBAplayer player = mapPlayerFromResultSet(result);
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(result, ps, conn);
        }

        return players;
    }

     */



    private List<NBAplayer> makeNBAplayer(ResultSet result) throws SQLException {
        List<NBAplayer> myNBAplayers = new ArrayList<>();
        while (result.next()) {
            try {
                NBAplayer player = new NBAplayer();
                player.setPlayerId(result.getInt("playerId"));
                player.setPlayerNumber(result.getInt("playerNumber"));
                player.setPlayerName(result.getString("playerName"));
                player.setPlayerPosition(result.getString("playerPosition"));
                player.setIsDeleted(result.getBoolean("isDeleted"));
                player.setTeamName(result.getString("teamName"));
                player.setTeamLocation(result.getString("teamLocation"));
                player.setTeamWins(result.getInt("teamWins"));
                player.setTeamLosses(result.getInt("teamLosses"));
                player.setTeamConf(result.getString("teamConf"));
                player.setTeamDiv(result.getString("teamDiv"));
                player.setPoints(result.getDouble("points"));
                player.setAssists(result.getDouble("assists"));
                player.setRebounds(result.getDouble("rebounds"));
                player.setSteals(result.getDouble("steals"));
                player.setBlocks(result.getDouble("blocks"));
                player.setGamesPlayed(result.getInt("gamesPlayed"));
                myNBAplayers.add(player);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return myNBAplayers;
    }

    private List<NBAplayer> makeNBAteam(ResultSet result) throws SQLException
    {
        List<NBAplayer> myNBAteam = new ArrayList<>();
        while(result.next())
        {
            try {
                NBAplayer team = new NBAplayer();
                team.setTeamName(result.getString("teamName"));
                team.setTeamLocation(result.getString("teamLocation"));
                team.setTeamConf(result.getString("teamConf"));
                team.setTeamDiv(result.getString("teamDiv"));
                team.setTeamWins(result.getInt("teamWins"));
                team.setTeamLosses(result.getInt("teamLosses"));

                myNBAteam.add(team);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return myNBAteam;
    }



    @Override
    public NBAplayer createNBAplayer(NBAplayer newNBAplayer) {
        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = MariaDbUtil.getConnection();

            String checkPlayerIdQuery = "SELECT playerId FROM NBAplayersTable WHERE playerId = ?";
            ps = conn.prepareStatement(checkPlayerIdQuery);
            ps.setInt(1, newNBAplayer.getPlayerId());
            rs = ps.executeQuery();

            if (rs.next()) {
                throw new IllegalArgumentException("Player ID " + newNBAplayer.getPlayerId() + " already taken.");
            }

            // Determine team-related details based on selected team name
            switch (newNBAplayer.getTeamName()) {
                case "Atlanta Hawks":
                    newNBAplayer.setTeamWins(36);
                    newNBAplayer.setTeamLosses(46);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Southeast Division");
                    break;
                case "Boston Celtics":
                    newNBAplayer.setTeamWins(48);
                    newNBAplayer.setTeamLosses(34);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Atlantic Division");
                    break;
                case "Brooklyn Nets":
                    newNBAplayer.setTeamWins(44);
                    newNBAplayer.setTeamLosses(38);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Atlantic Division");
                    break;
                case "Charlotte Hornets":
                    newNBAplayer.setTeamWins(38);
                    newNBAplayer.setTeamLosses(44);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Southeast Division");
                    break;
                case "Chicago Bulls":
                    newNBAplayer.setTeamWins(31);
                    newNBAplayer.setTeamLosses(51);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Central Division");
                    break;
                case "Cleveland Cavaliers":
                    newNBAplayer.setTeamWins(21);
                    newNBAplayer.setTeamLosses(61);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Central Division");
                    break;
                case "Dallas Mavericks":
                    newNBAplayer.setTeamWins(51);
                    newNBAplayer.setTeamLosses(31);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Southwest Division");
                    break;
                case "Denver Nuggets":
                    newNBAplayer.setTeamWins(47);
                    newNBAplayer.setTeamLosses(35);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Northwest Division");
                    break;
                case "Detroit Pistons":
                    newNBAplayer.setTeamWins(32);
                    newNBAplayer.setTeamLosses(50);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Central Division");
                    break;
                case "Golden State Warriors":
                    newNBAplayer.setTeamWins(48);
                    newNBAplayer.setTeamLosses(34);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Pacific Division");
                    break;
                case "Houston Rockets":
                    newNBAplayer.setTeamWins(20);
                    newNBAplayer.setTeamLosses(62);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Southwest Division");
                    break;
                case "Indiana Pacers":
                    newNBAplayer.setTeamWins(34);
                    newNBAplayer.setTeamLosses(48);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Central Division");
                    break;
                case "Los Angeles Clippers":
                    newNBAplayer.setTeamWins(39);
                    newNBAplayer.setTeamLosses(43);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Pacific Division");
                    break;
                case "Los Angeles Lakers":
                    newNBAplayer.setTeamWins(31);
                    newNBAplayer.setTeamLosses(51);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Pacific Division");
                    break;
                case "Memphis Grizzlies":
                    newNBAplayer.setTeamWins(39);
                    newNBAplayer.setTeamLosses(43);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Southwest Division");
                    break;
                case "Miami Heat":
                    newNBAplayer.setTeamWins(55);
                    newNBAplayer.setTeamLosses(27);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Southeast Division");
                    break;
                case "Milwaukee Bucks":
                    newNBAplayer.setTeamWins(50);
                    newNBAplayer.setTeamLosses(32);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Central Division");
                    break;
                case "Minnesota Timberwolves":
                    newNBAplayer.setTeamWins(23);
                    newNBAplayer.setTeamLosses(59);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Northwest Division");
                    break;
                case "New Orleans Pelicans":
                    newNBAplayer.setTeamWins(39);
                    newNBAplayer.setTeamLosses(43);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Southwest Division");
                    break;
                case "New York Knicks":
                    newNBAplayer.setTeamWins(48);
                    newNBAplayer.setTeamLosses(34);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Atlantic Division");
                    break;
                case "Oklahoma City Thunder":
                    newNBAplayer.setTeamWins(24);
                    newNBAplayer.setTeamLosses(58);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Northwest Division");
                    break;
                case "Orlando Magic":
                    newNBAplayer.setTeamWins(21);
                    newNBAplayer.setTeamLosses(61);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Southeast Division");
                    break;
                case "Philadelphia 76ers":
                    newNBAplayer.setTeamWins(52);
                    newNBAplayer.setTeamLosses(30);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Atlantic Division");
                    break;
                case "Phoenix Suns":
                    newNBAplayer.setTeamWins(51);
                    newNBAplayer.setTeamLosses(31);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Pacific Division");
                    break;
                case "Portland Trail Blazers":
                    newNBAplayer.setTeamWins(44);
                    newNBAplayer.setTeamLosses(38);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Northwest Division");
                    break;
                case "Sacramento Kings":
                    newNBAplayer.setTeamWins(31);
                    newNBAplayer.setTeamLosses(51);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Pacific Division");
                    break;
                case "San Antonio Spurs":
                    newNBAplayer.setTeamWins(33);
                    newNBAplayer.setTeamLosses(49);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Southwest Division");
                    break;
                case "Toronto Raptors":
                    newNBAplayer.setTeamWins(45);
                    newNBAplayer.setTeamLosses(37);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Atlantic Division");
                    break;
                case "Utah Jazz":
                    newNBAplayer.setTeamWins(52);
                    newNBAplayer.setTeamLosses(30);
                    newNBAplayer.setTeamConf("Western Conference");
                    newNBAplayer.setTeamDiv("Northwest Division");
                    break;
                case "Washington Wizards":
                    newNBAplayer.setTeamWins(34);
                    newNBAplayer.setTeamLosses(48);
                    newNBAplayer.setTeamConf("Eastern Conference");
                    newNBAplayer.setTeamDiv("Southeast Division");
                    break;
                default:
                    break;
            }


            ps = conn.prepareStatement(insertNBAplayer);

            ps.setInt(1, newNBAplayer.getPlayerId());
            ps.setInt(2, newNBAplayer.getPlayerNumber());
            ps.setString(3, newNBAplayer.getPlayerName());
            ps.setString(4, newNBAplayer.getPlayerPosition());
            ps.setBoolean(5, newNBAplayer.getIsDeleted());
            ps.setString(6, newNBAplayer.getTeamName());
            ps.setString(7, newNBAplayer.getTeamLocation());
            ps.setInt(8, newNBAplayer.getTeamWins());
            ps.setInt(9, newNBAplayer.getTeamLosses());
            ps.setString(10, newNBAplayer.getTeamConf());
            ps.setString(11, newNBAplayer.getTeamDiv());
            ps.setDouble(12, newNBAplayer.getPoints());
            ps.setDouble(13, newNBAplayer.getAssists());
            ps.setDouble(14, newNBAplayer.getRebounds());
            ps.setDouble(15, newNBAplayer.getSteals());
            ps.setDouble(16, newNBAplayer.getBlocks());
            ps.setInt(17, newNBAplayer.getGamesPlayed());

            ps.executeUpdate();
            System.out.println("Update Done.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return newNBAplayer;
    }





    @Override
    public NBAplayer updateNBAplayer(NBAplayer updateNBAplayer) {
        List<NBAplayer> myNBAplayer = getPlayerById(updateNBAplayer.getPlayerId());

        if (myNBAplayer != null) {
            PreparedStatement ps = null;
            Connection conn = MariaDbUtil.getConnection();

            try {
                ps = conn.prepareStatement(updatePlayerById);
                ps.setString(1, updateNBAplayer.getPlayerName());
                ps.setInt(2, updateNBAplayer.getPlayerNumber());
                ps.setString(3, updateNBAplayer.getPlayerPosition());
                ps.setString(4, updateNBAplayer.getTeamName());
                ps.setString(5, updateNBAplayer.getTeamLocation());
                ps.setInt(6, updateNBAplayer.getTeamWins());
                ps.setInt(7, updateNBAplayer.getTeamLosses());
                ps.setString(8, updateNBAplayer.getTeamConf());
                ps.setString(9, updateNBAplayer.getTeamDiv());
                ps.setDouble(10, updateNBAplayer.getPoints());
                ps.setDouble(11, updateNBAplayer.getAssists());
                ps.setDouble(12, updateNBAplayer.getRebounds());
                ps.setDouble(13, updateNBAplayer.getSteals());
                ps.setDouble(14, updateNBAplayer.getBlocks());
                ps.setInt(15, updateNBAplayer.getGamesPlayed());
                ps.setInt(16, updateNBAplayer.getPlayerId()); // Parameter for WHERE clause

                int updateRowCount = ps.executeUpdate();
                System.out.println("Update Row Count: " + updateRowCount);

                if (updateRowCount > 0) {
                    return updateNBAplayer; // Return updated player object
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null; // Return null if update fails
    }





    @Override
    public NBAplayer softDeletePlayers(boolean isDeleted, int playerId) {
        List<NBAplayer> myNBAplayer = getPlayerById(playerId);
        NBAplayer playerToDelete = null;
        if (myNBAplayer != null) {
            playerToDelete = myNBAplayer.get(0);
            int updateRowCount = 0;
            PreparedStatement ps = null;
            Connection conn = MariaDbUtil.getConnection();

            try {
                ps = conn.prepareStatement(softDeletePlayersById);
                ps.setInt(1, playerId);
                updateRowCount = ps.executeUpdate();
                System.out.println("Delete Row Count: " + updateRowCount);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return playerToDelete;
    }

    @Override
    public void restoreDeletedPlayers() {
        try (Connection conn = MariaDbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(restoreAllDeletedPlayers)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    private List<Player> makePlayer(ResultSet result) throws SQLException {
        List<Player> players = new ArrayList<>();
        Connection conn = MariaDbUtil.getConnection();
        while (result.next()) {
            Player player = new Player();
            player.setId(result.getInt("playerID"));
            player.setName(result.getString("playerName"));
            player.setNumber(result.getInt("playerNumber"));
            player.setLeague(result.getString("playerLeague"));
            player.setPosition(result.getString("playerPosition"));
            int teamID = result.getInt("teamID");
            Teams team = fetchTeamById(teamID, conn);
            player.setTeam(team);
            players.add(player);
        }
        return players;
    }

    private Teams fetchTeamById(int teamID, Connection conn) throws SQLException {
        String selectTeamById = "SELECT * FROM teamsTable WHERE teamID = ?";
        PreparedStatement ps = conn.prepareStatement(selectTeamById);
        ps.setInt(1, teamID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Teams team = new Teams();
            team.setTeamID(rs.getInt("teamID"));
            team.setTeamName(rs.getString("teamName"));
            team.setTeamLeague(rs.getString("teamLeague"));
            team.setTeamLocation(rs.getString("teamLocation"));
            return team;
        }
        return null;
    }
    private List<Teams> makeTeams(ResultSet result) throws SQLException {
        List<Teams> teams = new ArrayList<>();
        while (result.next()) {
            Teams team = new Teams();
            team.setTeamID(result.getInt("teamID"));
            team.setTeamName(result.getString("teamName"));
            team.setTeamLeague(result.getString("teamLeague"));
            team.setTeamLocation(result.getString("teamLocation"));
            teams.add(team);
        }
        return teams;
    }

}
