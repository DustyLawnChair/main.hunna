(function() {
    var playtracker = angular.module('playtracker');

    playtracker.controller('searchController', function($scope, $http, $location, $routeParams) {
        $scope.appName = 'My Stats Application';
        $scope.players = [];
        $scope.searchType = 'Players'; // Default search type
        $scope.searchQuery = ''; // Initialize searchQuery as an empty string
        $scope.orderByValue = 'playerId'; // Initialize orderByValue
        $scope.reverse = false; // Initialize reverse to false

        function fetchPlayers(playerId) {
            if (playerId) {
                $http.get("http://localhost:8080/playtracker/players/search/" + playerId)
                    .then(function(response) {
                        $location.path('/players/stats/' + playerId);
                    })
                    .catch(function(response) {
                        console.log('Error http GET player by ID:', response.status);
                    });
            } else {
                $http.get("http://localhost:8080/playtracker/players")
                    .then(function(response) {
                        $scope.players = response.data;
                    })
                    .catch(function(response) {
                        console.log('Error http GET players:', response.status);
                    });
            }
        }

        $scope.orderByColumn = function(column) {
            $scope.orderByValue = column;
            $scope.reverse = !$scope.reverse; // Simplified reverse logic
        };

        $scope.setSearchType = function(type) {
            $scope.searchType = type;
        };

        $scope.updateSearchQuery = function() {
            console.log('Search query updated:', $scope.searchQuery);
        };

        $scope.performSearch = function() {
            console.log('performSearch called.');
            console.log('Search type:', $scope.searchType);
            console.log('Search query:', $scope.searchQuery);

            if ($scope.searchType === 'Players') {
                searchPlayers($scope.searchQuery);
            } else if ($scope.searchType === 'Teams') {
                searchTeams($scope.searchQuery);
            }
        };

        function searchPlayers(query) {
            console.log('Searching players with query:', query);
            $http.get("http://localhost:8080/playtracker/players/search/byName?query=" + query)
                .then(function(response) {
                    $scope.players = response.data;
                })
                .catch(function(error) {
                    console.log('Error searching players:', error);
                });
        }

        function searchTeams(query) {
            console.log('Searching teams with query:', query);
            $http.get("http://localhost:8080/playtracker/teams/" + query + "/players")
                .then(function(response) {
                    $scope.players = response.data;
                })
                .catch(function(error) {
                    console.log('Error searching teams:', error.status, error.data);
                });
        }

        $scope.restorePlayers = function() {
            $http.put("http://localhost:8080/playtracker/players/restoreAll")
                .then(function(response) {
                    $scope.updateStatus = 'Players successfully restored.';
                    $location.path('/search');
                }, function(response) {
                    $scope.updateStatus = 'Error restoring players.';
                    console.log("Error restoring players: " + response.data);
                });
        };

        $scope.goToPlayerView = function(playerId) {
            $location.path('/players/stats/' + playerId);
        };

        $scope.goToUpdateView = function(playerId) {
            var player = $scope.players.find(function(player) {
                return player.id === playerId;
            });

            $location.path('/update/' + playerId).search({teamName: player.teamName});
        };

        if ($routeParams.playerId) {
            fetchPlayers($routeParams.playerId);
        } else {
            fetchPlayers();
        }
    });
})();
