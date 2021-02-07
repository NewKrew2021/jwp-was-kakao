String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

logout = function () {
  removeCookie("logined");
  goHome();
};

removeCookie = function(key) {
  let date = new Date();
  document.cookie = key + "= " + "; expired=" + date.toUTCString() + "; path=/";
};

goHome = function() {
  location.href = "/index.html";
};
