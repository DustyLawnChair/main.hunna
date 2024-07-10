package com.PlayTrackerWebApp.playtracker.model.teams;

public enum NBA_Teams {
    sixers("76ers"), Bucks("Bucks"), Bulls("Bulls"), Cavaliers("Cavaliers"), Celtics("Celtics"),
    Clippers("Clippers"), Grizzlies("Grizzlies"), Hawks("Hawks"), Heat("Heat"), Hornets("Hornets"),
    Jazz("Jazz"), Kings("Kings"), Knicks("Knicks"), Lakers("Lakers"), Magic("Magic"), Mavericks("Mavericks"),
    Nets("Nets"), Nuggets("Nuggets"), Pacers("Pacers"), Pelicans("Pelicans"), Pistons("Pistons"),
    Raptors("Raptors"), Rockets("Rockets"), Spurs("Spurs"), Suns("Suns"), Thunder("Thunder"),
    Timberwolves("Timberwolves"), Blazers("Blazers"), Warriors("Warriors"), Wizards("Wizards");

    private final String teamName;

    NBA_Teams(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
