var exec = require('cordova/exec');

exports.coolMethod = function(arg0, success, error) {
    exec(success, error, "Seguranca", "coolMethod", [arg0]);
};

exports.Encrypt = function(arg0, success, error) {
    exec(success, error, "Seguranca", "Encrypt", [arg0]);
};

exports.Decrypt = function(arg0, success, error) {
    exec(success, error, "Seguranca", "Decrypt", [arg0]);
};
