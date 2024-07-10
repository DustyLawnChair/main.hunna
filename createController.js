(function() {
    var playtracker = angular.module('playtracker');

    playtracker.controller('createController', ['$scope', '$http', function($scope, $http) {


        /*
        $scope.player = {
            playerId: null,
            playerName: '',
            playerNumber: null,
            teamName: '',
            playerPosition: '',
            isDeleted: false,
            teamLocation: 'a',
            teamWins: 0,
            teamLosses: 0,
            teamConf: teamConf,
            teamDiv: 'a',
            points: 1.0,
            assists: 1.0,
            rebounds: 1.0,
            steals: 1.0,
            blocks: 1.0,
            gamesPlayed: 1
        };
        */

        $scope.teams = [
            'Atlanta Hawks', 'Boston Celtics', 'Brooklyn Nets', 'Charlotte Hornets', 'Chicago Bulls',
            'Cleveland Cavaliers', 'Dallas Mavericks', 'Denver Nuggets', 'Detroit Pistons', 'Golden State Warriors',
            'Houston Rockets', 'Indiana Pacers', 'LA Clippers', 'Los Angeles Lakers', 'Memphis Grizzlies',
            'Miami Heat', 'Milwaukee Bucks', 'Minnesota Timberwolves', 'New Orleans Pelicans', 'New York Knicks',
            'Oklahoma City Thunder', 'Orlando Magic', 'Philadelphia 76ers', 'Phoenix Suns', 'Portland Trail Blazers',
            'Sacramento Kings', 'San Antonio Spurs', 'Toronto Raptors', 'Utah Jazz', 'Washington Wizards'
        ];
        

        $scope.positions = ['PG', 'SG', 'SF', 'PF', 'C'];

        $scope.createNBAplayer = function() {

            let teamLocation = '';
            let teamWins = 0;
            let teamLosses = 0;
            let teamConf = '';
            let teamDiv = '';


            if ($scope.player.teamName === 'Atlanta Hawks') {
                $scope.player.teamLocation = 'Atlanta, Georgia';
                $scope.player.teamWins = 36;
                $scope.player.teamLosses = 46;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Southeast Division';
            } else if ($scope.player.teamName === 'Boston Celtics') {
                $scope.player.teamLocation = 'Boston, Massachusetts';
                $scope.player.teamWins = 48;
                $scope.player.teamLosses = 34;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Atlantic Division';
            } else if ($scope.player.teamName === 'Brooklyn Nets') {
                $scope.player.teamLocation = 'Brooklyn, New York';
                $scope.player.teamWins = 44;
                $scope.player.teamLosses = 38;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Atlantic Division';
            } else if ($scope.player.teamName === 'Charlotte Hornets') {
                $scope.player.teamLocation = 'Charlotte, North Carolina';
                $scope.player.teamWins = 38;
                $scope.player.teamLosses = 44;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Southeast Division';
            } else if ($scope.player.teamName === 'Chicago Bulls') {
                $scope.player.teamLocation = 'Chicago, Illinois';
                $scope.player.teamWins = 31;
                $scope.player.teamLosses = 51;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Central Division';
            } else if ($scope.player.teamName === 'Cleveland Cavaliers') {
                $scope.player.teamLocation = 'Cleveland, Ohio';
                $scope.player.teamWins = 21;
                $scope.player.teamLosses = 61;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Central Division';
            } else if ($scope.player.teamName === 'Dallas Mavericks') {
                $scope.player.teamLocation = 'Dallas, Texas';
                $scope.player.teamWins = 51;
                $scope.player.teamLosses = 31;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Southwest Division';
            } else if ($scope.player.teamName === 'Denver Nuggets') {
                $scope.player.teamLocation = 'Denver, Colorado';
                $scope.player.teamWins = 47;
                $scope.player.teamLosses = 35;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Northwest Division';
            } else if ($scope.player.teamName === 'Detroit Pistons') {
                $scope.player.teamLocation = 'Detroit, Michigan';
                $scope.player.teamWins = 32;
                $scope.player.teamLosses = 50;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Central Division';
            } else if ($scope.player.teamName === 'Golden State Warriors') {
                $scope.player.teamLocation = 'San Francisco, California';
                $scope.player.teamWins = 48;
                $scope.player.teamLosses = 34;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Pacific Division';
            } else if ($scope.player.teamName === 'Houston Rockets') {
                $scope.player.teamLocation = 'Houston, Texas';
                $scope.player.teamWins = 20;
                $scope.player.teamLosses = 62;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Southwest Division';
            } else if ($scope.player.teamName === 'Indiana Pacers') {
                $scope.player.teamLocation = 'Indianapolis, Indiana';
                $scope.player.teamWins = 34;
                $scope.player.teamLosses = 48;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Central Division';
            } else if ($scope.player.teamName === 'Los Angeles Clippers') {
                $scope.player.teamLocation = 'Los Angeles, California';
                $scope.player.teamWins = 39;
                $scope.player.teamLosses = 43;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Pacific Division';
            } else if ($scope.player.teamName === 'Los Angeles Lakers') {
                $scope.player.teamLocation = 'Los Angeles, California';
                $scope.player.teamWins = 31;
                $scope.player.teamLosses = 51;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Pacific Division';
            } else if ($scope.player.teamName === 'Memphis Grizzlies') {
                $scope.player.teamLocation = 'Memphis, Tennessee';
                $scope.player.teamWins = 39;
                $scope.player.teamLosses = 43;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Southwest Division';
            } else if ($scope.player.teamName === 'Miami Heat') {
                $scope.player.teamLocation = 'Miami, Florida';
                $scope.player.teamWins = 55;
                $scope.player.teamLosses = 27;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Southeast Division';
            } else if ($scope.player.teamName === 'Milwaukee Bucks') {
                $scope.player.teamLocation = 'Milwaukee, Wisconsin';
                $scope.player.teamWins = 50;
                $scope.player.teamLosses = 32;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Central Division';
            } else if ($scope.player.teamName === 'Minnesota Timberwolves') {
                $scope.player.teamLocation = 'Minneapolis, Minnesota';
                $scope.player.teamWins = 23;
                $scope.player.teamLosses = 59;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Northwest Division';
            } else if ($scope.player.teamName === 'New Orleans Pelicans') {
                $scope.player.teamLocation = 'New Orleans, Louisiana';
                $scope.player.teamWins = 39;
                $scope.player.teamLosses = 43;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Southwest Division';
            } else if ($scope.player.teamName === 'New York Knicks') {
                $scope.player.teamLocation = 'New York, New York';
                $scope.player.teamWins = 48;
                $scope.player.teamLosses = 34;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Atlantic Division';
            } else if ($scope.player.teamName === 'Oklahoma City Thunder') {
                $scope.player.teamLocation = 'Oklahoma City, Oklahoma';
                $scope.player.teamWins = 24;
                $scope.player.teamLosses = 58;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Northwest Division';
            } else if ($scope.player.teamName === 'Orlando Magic') {
                $scope.player.teamLocation = 'Orlando, Florida';
                $scope.player.teamWins = 21;
                $scope.player.teamLosses = 61;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Southeast Division';
            } else if ($scope.player.teamName === 'Philadelphia 76ers') {
                $scope.player.teamLocation = 'Philadelphia, Pennsylvania';
                $scope.player.teamWins = 52;
                $scope.player.teamLosses = 30;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Atlantic Division';
            } else if ($scope.player.teamName === 'Phoenix Suns') {
                $scope.player.teamLocation = 'Phoenix, Arizona';
                $scope.player.teamWins = 51;
                $scope.player.teamLosses = 31;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Pacific Division';
            } else if ($scope.player.teamName === 'Portland Trail Blazers') {
                $scope.player.teamLocation = 'Portland, Oregon';
                $scope.player.teamWins = 44;
                $scope.player.teamLosses = 38;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Northwest Division';
            } else if ($scope.player.teamName === 'Sacramento Kings') {
                $scope.player.teamLocation = 'Sacramento, California';
                $scope.player.teamWins = 31;
                $scope.player.teamLosses = 51;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Pacific Division';
            } else if ($scope.player.teamName === 'San Antonio Spurs') {
                $scope.player.teamLocation = 'San Antonio, Texas';
                $scope.player.teamWins = 33;
                $scope.player.teamLosses = 49;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Southwest Division';
            } else if ($scope.player.teamName === 'Toronto Raptors') {
                $scope.player.teamLocation = 'Toronto, Ontario';
                $scope.player.teamWins = 45;
                $scope.player.teamLosses = 37;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Atlantic Division';
            } else if ($scope.player.teamName === 'Utah Jazz') {
                $scope.player.teamLocation = 'Salt Lake City, Utah';
                $scope.player.teamWins = 52;
                $scope.player.teamLosses = 30;
                $scope.player.teamConf = 'Western Conference';
                $scope.player.teamDiv = 'Northwest Division';
            } else if ($scope.player.teamName === 'Washington Wizards') {
                $scope.player.teamLocation = 'Washington, D.C.';
                $scope.player.teamWins = 34;
                $scope.player.teamLosses = 48;
                $scope.player.teamConf = 'Eastern Conference';
                $scope.player.teamDiv = 'Southeast Division';
            } else {
                $scope.player.teamLocation = '';
                $scope.player.teamWins = 0;
                $scope.player.teamLosses = 0;
                $scope.player.teamConf = '';
                $scope.player.teamDiv = '';
            }
            
            
                $scope.player.points = 34.4;
                $scope.player.assists = 1.3;
                $scope.player.rebounds = 4.5;
                $scope.player.steals = 0.8;
                $scope.player.blocks = 9.2;

            $http.post('http://localhost:8080/playtracker/players', $scope.player)
            .then(function(response) {
                $scope.createStatus = 'Player creation successful';
                $scope.disableCreate = true;
                $scope.clear();
            }, function(response) {
                $scope.createStatus = response.data.message || 'Error creating player';
                console.log('Error http POST NBA players:', response.status, response.data);
            });
        };

        $scope.clear = function() {
            $scope.player = {
                playerId: null,
                playerName: '',
                playerNumber: null,
                teamName: '',
                playerPosition: '',
                isDeleted: false,
                teamLocation: '',
                teamWins: 0,
                teamLosses: 0,
                teamConf: '',
                teamDiv: '',
                points: 1.0,
                assists: 1.0,
                rebounds: 1.0,
                steals: 1.0,
                blocks: 1.0,
                gamesPlayed: 1
            };
            $scope.createForm.$setPristine();
            $scope.createForm.$setUntouched();
            $scope.createStatus = '';
        };

    }]);
})();
