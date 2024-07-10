package com.PlayTrackerWebApp.playtracker.service;

import com.PlayTrackerWebApp.playtracker.dao.StatsDao;
import com.PlayTrackerWebApp.playtracker.dao.StatsDaoImpl;
import com.PlayTrackerWebApp.playtracker.model.Leagues;
import com.PlayTrackerWebApp.playtracker.model.Teams;
import com.PlayTrackerWebApp.playtracker.model.players.NBAplayer;
import com.PlayTrackerWebApp.playtracker.model.players.Player;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {
    private StatsDao statsDao;

    @Autowired
    public StatsService(StatsDao statsDao)
    {
        this.statsDao = statsDao;
    }

    public List<NBAplayer> getPlayers()
    {
        return statsDao.getPlayers();
    }

    public List<NBAplayer> getTeams()
    {
        return statsDao.getTeams();
    }

    public List<NBAplayer> getPlayerStats(int playerId)
    {
        return statsDao.getPlayerStats(playerId);
    }


    public List<NBAplayer> getPlayersByTeam(String teamName)
    {
        return statsDao.getPlayersByTeam(teamName);
    }


    public List<NBAplayer> getPlayersById(int playerId)
    {
        return statsDao.getPlayerById(playerId);
    }

    /*
    public List<NBAplayer> getPlayersByFancySearch(Boolean guard, Boolean forward, Boolean center,
                                                   String conference, Double points, Double assists,
                                                   Double rebounds, Double steals, Double blocks) {
        return statsDao.getPlayersByFancySearch(guard, forward, center, conference, points, assists, rebounds, steals, blocks);
    }

     */


    public NBAplayer createNBAplayer(NBAplayer newNBAplayer)
    {
        return statsDao.createNBAplayer(newNBAplayer);
    }
    public NBAplayer updateNBAplayer(NBAplayer updateNBAplayer) {
        return statsDao.updateNBAplayer(updateNBAplayer);
    }

    public NBAplayer softDeletePlayers(boolean isDeleted, int playerId) {
        return statsDao.softDeletePlayers(isDeleted, playerId);
    }

    public void restoreDeletedPlayers()
    {
        statsDao.restoreDeletedPlayers();
    }

    public List<NBAplayer> getPlayersByName(String name)
    {
        return statsDao.getPlayersByName(name);
    }

}
