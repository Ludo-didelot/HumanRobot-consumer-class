var storeModule = angular.module('initApplication', ['ngRoute','ngResource']);

storeModule.config(['$routeProvider', function($routeProvider) {
        $routeProvider
        .when('/', {
            templateUrl:    'html/item.html',
            controller:     'itemCtrlWithResource',
            resolve:        {items: getItems}
        })

}]);

function getItems(itemServiceWithResource)
{
        //return PetService.getPets();
        return itemServiceWithResource.query();
}
