(function() {
    var playtracker = angular.module('playtracker');

    playtracker.controller('updateController', function($scope, $http, $routeParams, $location, $timeout) {
        $scope.appName = 'PlayTracker Application';
        $scope.player = {};
        $scope.teams = [];
        $scope.updateStatus = '';

        console.log('Route Parameters:', $routeParams);

        // Function to fetch teams from backend
        function fetchTeams() {
            $http.get("http://localhost:8080/playtracker/teams")
                .then(function(response) {
                    $scope.teams = response.data;
                }, function(response) {
                    console.log('Error fetching teams: ' + response.status);
                });
        }

        // Function to fetch player stats by player ID
        $scope.fetchPlayerStats = function(playerId) {
            console.log('Fetching stats for player ID:', playerId);
            $http.get("http://localhost:8080/playtracker/players/search/" + playerId)
                .then(function(response) {
                    console.log("Player stats fetched:", response.data);
                    $scope.player = response.data[0]; // Access the first element of the array
                }, function(error) {
                    console.error('Error fetching player stats: ', error);
                });
        };

        // Fetch player stats if player ID is provided in route parameters
        if ($routeParams.playerId) {
            $scope.fetchPlayerStats($routeParams.playerId);
        } else {
            console.error('Player ID is undefined.');
        }

        // Function to update player details
        $scope.updatePlayer = function(playerId) {
            $http.put("http://localhost:8080/playtracker/players/update/" + playerId, $scope.player)
                .then(function(response) {
                    $scope.updateStatus = 'Player Update Successful';
                    $timeout(function() {
                        $location.path('/players/stats/' + playerId);
                    }, 2000);
                }, function(response) {
                    $scope.updateStatus = 'Error trying to update player';
                    console.log("Error HTTP PUT players: " + response.status);
                });
        };

        $scope.restorePlayers = function() {
            $http.put("http://localhost:8080/playtracker/players/restoreAll")
                .then(function(response) {
                    $scope.updateStatus = 'Players successfully restored.';
                }, function(response) {
                    $scope.updateStatus = 'Error restoring players.';
                    console.log("Error restoring players: " + response.data);
                });
        };
                
        // Function to delete a player
        $scope.deletePlayer = function() {
            var playerId = $scope.player.playerId; // Assuming player ID is stored in player object
            if (playerId) {
                $http.delete("http://localhost:8080/playtracker/players/delete/" + playerId)
                    .then(function(response) {
                        $scope.updateStatus = 'Player Delete Successful';
                        $location.path('/search'); // Redirect to search page after deletion
                    }, function(response) {
                        console.log('Error deleting player: ' + response.status);
                    });
            } else {
                console.log('Player ID is null or undefined.');
            }
        };

        // Function to navigate back to search view
        $scope.goToSearchView = function() {
            $location.path('/search');
        };

        // Fetch teams when controller initializes
        fetchTeams();

        // Define player positions
        $scope.positions = ['PG', 'SG', 'SF', 'PF', 'C'];
    });
})();
