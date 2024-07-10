var playtracker = angular.module('playtracker', ['ngRoute']);

playtracker.config(['$routeProvider', function($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "main.html"
        })
        .when("/search", {
            templateUrl: "search.html",
            controller: "searchController"
        })
        .when("/search/:playerId", {
            templateUrl: "search.html",
            controller: "searchController"
        })
        .when("/create", {
            templateUrl: "create.html",
            controller: "createController"
        })
        .when("/fancy", {
            templateUrl: "fancy.html",
            controller: "fancyController"
        })
        .when("/stack", {
            templateUrl: "stack.html"
        })
        .when("/resume", {
            templateUrl: "resume.html"
        })
        .when("/link2", {
            templateUrl: "link2.html"
        })
        .when("/update/:playerId", {
            templateUrl: "update.html",
            controller: "updateController"
        })
        .when("/delete/:playerId", {
            templateUrl: "update.html",
            controller: "updateController"
        })
        .when("/players/restoreAll", {
            templateUrl: "search.html",
            controller: "updateController"
        })
        .when("/players/stats/:playerId", {
            templateUrl: "player.html",
            controller: "updateController"
        })
        .otherwise({
            redirectTo: "main.html"
        });
}]);
