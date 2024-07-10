package com.PlayTrackerWebApp.playtracker.controller;

import com.PlayTrackerWebApp.playtracker.model.Leagues;
import com.PlayTrackerWebApp.playtracker.model.Teams;
import com.PlayTrackerWebApp.playtracker.model.players.NBAplayer;
import com.PlayTrackerWebApp.playtracker.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "playtracker", produces = "application/json")
@CrossOrigin("*")
public class StatsController {

    private final StatsService service;

    @Autowired
    public StatsController(StatsService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/players")
    public List<NBAplayer> getAllPlayers() {
        return service.getPlayers();
    }

    @GetMapping("/teams")
    public List<NBAplayer> getAllTeams()
    {
        return service.getTeams();
    }

    @GetMapping("/players/search/byName")
    public List<NBAplayer> getPlayersByName(@RequestParam String query)
    {
        return service.getPlayersByName(query);
    }


    @GetMapping("/teams/{teamName}/players")
    public List<NBAplayer> getPlayersByTeam(@PathVariable String teamName) {
        return service.getPlayersByTeam(teamName);
    }


    @GetMapping("/players/search/{playerId}")
    public List<NBAplayer> getPlayerById(@PathVariable int playerId)
    {
        return service.getPlayersById(playerId);
    }

    @GetMapping("/players/stats/{playerId}")
    public List<NBAplayer> getPlayerStats(@PathVariable int playerId)
    {
        return service.getPlayerStats(playerId);
    }

    /*
    @GetMapping("/players/fancySearch")
    public List<NBAplayer> getPlayersByFancySearch(
            @RequestParam(required = false) Boolean guard,
            @RequestParam(required = false) Boolean forward,
            @RequestParam(required = false) Boolean center,
            @RequestParam(required = false) String conference,
            @RequestParam(required = false) Double points,
            @RequestParam(required = false) Double assists,
            @RequestParam(required = false) Double rebounds,
            @RequestParam(required = false) Double steals,
            @RequestParam(required = false) Double blocks) {

        // Call service method with fancy search parameters
        return service.getPlayersByFancySearch(guard, forward, center, conference, points, assists, rebounds, steals, blocks);
    }

     */


    @PostMapping(value = "/players", consumes = "application/json")
    public NBAplayer createNBAplayer(@RequestBody NBAplayer newNBAplayer)
    {
        System.out.println("Received Team Location: " + newNBAplayer.getTeamLocation());
        return service.createNBAplayer(newNBAplayer);
    }

    @PutMapping(value = "/players/update/{playerId}", consumes = "application/json")
    public NBAplayer updateNBAPlayer(@PathVariable int playerId, @RequestBody NBAplayer updateNBAplayer) throws StatsException {
        try {
            updateNBAplayer.setPlayerId(playerId);
            NBAplayer NBAplayer = updateNBAplayer;

            return service.updateNBAplayer(updateNBAplayer);
        } catch (Exception e) {
            throw new StatsException("Failed to update player.", e);
        }
    }

    @DeleteMapping("/players/delete/{playerId}")
    public NBAplayer softDeletePlayers(@PathVariable int playerId, boolean isDeleted) {
        return service.softDeletePlayers(isDeleted, playerId);
    }

    @PutMapping("/players/restoreAll")
    public void restoreDeletedPlayers()
    {
        service.restoreDeletedPlayers();
    }












}
