storeModule.factory('itemService', ['$http', '$q', function($http, $q) {
        var service = {
            getItems: function() {
            			var url = "/item";

            			var deferred = $q.defer();

            			$http.get(url).then(function(response) {
            					deferred.resolve(response.data);
            				}, function(response) {
            					deferred.reject(response.data.message);
            			});
            			return deferred.promise;
            		}
        };

        return service;
}])

storeModule.factory('itemServiceWithResource', ['$resource', function($resource) {
        return $resource("/item/:id", {id: "@id"}, {
                update: {
                    method: 'PUT'
                }
            });


     /*return {
            getItems: function() {
                        return $resource('/item').query();
                    },

            deleteItem: function(id, callback) {
                        var rest = $resource('/item/:id', {id:id}, null,
                            {
                                'delete': { method:'DELETE' }
                            });
                        rest.delete(id, function(results) {
                            callback(results);
                        });
                    },
        }*/
}])


storeModule.factory('AuthInterceptor', [function() {
    return {
    // Send the Authorization header with each request
        'request': function(config) {
            config.headers = config.headers || {};
            var encodedString = btoa("myhu:letmein12345");
            config.headers.Authorization = 'Basic '+encodedString;
           return config;
        }
    };
}]);