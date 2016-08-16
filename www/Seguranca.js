var exec = require('cordova/exec');

exports.Encrypt = function(arg0,arg1, success, error) {
    exec(function(p) {
        success(p);
    }, error, "Seguranca", "Encrypt", [arg0,arg1]);
};

exports.Decrypt = function(arg0,arg1, success, error) {
    exec(success, error, "Seguranca", "Decrypt", [arg0,arg1]);
};
