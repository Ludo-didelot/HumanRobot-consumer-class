storeModule.controller('itemsCtrl', ['$scope', 'itemService', 'list', function($scope, itemService, list ) {
        $scope.list = list;
    }]);

storeModule.controller('itemCtrlWithResource', ['$scope', '$window', 'itemServiceWithResource', 'items', function($scope, $window, itemServiceWithResource, items){

    $scope.items = items;

        $scope.fetchAll = function(){
        	  $scope.items = itemServiceWithResource.query();
          };


    $scope.deleteItem = function(identity)
    {
        var service = new itemServiceWithResource({id:identity});
        service.$delete(function(){
            $scope.fetchAll();
        });
    }

    $scope.submit = function()
    {
        var item = {
            'name'      : $scope.name,
            'category'  : $scope.category,
            'uri'       : $scope.uri
        };
        var service = new itemServiceWithResource(item);
        service.$save(function(response){
                $window.location.href="/";
        });
    }


}]);

