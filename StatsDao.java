package com.PlayTrackerWebApp.playtracker.dao;

import com.PlayTrackerWebApp.playtracker.model.players.NBAplayer;
import com.PlayTrackerWebApp.playtracker.model.players.Player;
import com.PlayTrackerWebApp.playtracker.model.Teams;
import com.PlayTrackerWebApp.playtracker.model.Leagues;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface StatsDao {
    List<NBAplayer> getPlayers();
    List<NBAplayer> getTeams();
    List<NBAplayer> getPlayersByTeam(String teamName);

    List<NBAplayer> getPlayerById(int playerId);
    List<NBAplayer> getPlayersByName(String name);
    List<NBAplayer> getPlayerStats(int playerId);
    /*
    List<NBAplayer> getPlayersByFancySearch(int playerId);

     */
    NBAplayer createNBAplayer(NBAplayer newNBAplayer);
    NBAplayer updateNBAplayer(NBAplayer updatePlayer);
    NBAplayer softDeletePlayers(boolean isDeleted, int playerId);
    void restoreDeletedPlayers();
}
