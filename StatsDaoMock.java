package com.PlayTrackerWebApp.playtracker.dao;

import com.PlayTrackerWebApp.playtracker.model.Leagues;
import com.PlayTrackerWebApp.playtracker.model.Teams;
import com.PlayTrackerWebApp.playtracker.model.players.*;
import com.PlayTrackerWebApp.playtracker.model.teams.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatsDaoMock implements StatsDao {

    private static final List<NBAplayer> NBAplayers = new ArrayList<>();
    private static final List<Teams> teams = new ArrayList<>();


    @Override
    public List<NBAplayer> getPlayers() {
        return new ArrayList<>(NBAplayers);
    }

    @Override
    public List<NBAplayer> getTeams() {
        return null;
    }

    @Override
    public List<NBAplayer> getPlayersByTeam(String teamName) {
        return null;
    }



    @Override
    public List<NBAplayer> getPlayerById(int playerId) {
        return null;
    }


    @Override
    public List<NBAplayer> getPlayersByName(String name) {
        return null;
    }

    @Override
    public List<NBAplayer> getPlayerStats(int playerId) {
        return null;
    }

    @Override
    public NBAplayer createNBAplayer(NBAplayer newNBAplayer) {
        return null;
    }


    @Override
    public NBAplayer updateNBAplayer(NBAplayer updatePlayer) {
        return null;
    }


    @Override
    public NBAplayer softDeletePlayers(boolean isDeleted, int playerId) {
        return null;
    }

    @Override
    public void restoreDeletedPlayers() {

    }

}
