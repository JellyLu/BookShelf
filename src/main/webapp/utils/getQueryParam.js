var getQueryParam = function (name) {
  var params = location.search.substr(1).split('&');
  var result = null;
  params.forEach(function (param) {
    var pair = param.split('=');
    if (pair[0] === name) {
      result = pair[1];
    }
  })
  return result;
};
