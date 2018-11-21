/*加密*/
function encrypt(string) {
    //实例化加密对象
    var encrypt = new JSEncrypt();
    //设置加密公钥
    encrypt.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE0wojG1fnUtJs/MYtTG6xqq9yE1vucbNV9JhTAosHP15z8DokLnPXTPs/fGXWDs3k0S9uZmQ/tQBHUKrn+reKL/I6r+86v1aUtaeL7FmirNpLFhkVRKCch81Kk2w9ePd/MRAjV4Y8osuBXEZVAls7G4I+5Nv4vV3InmT6PpAd7QIDAQAB");

    //分段加密
    var encryptResult =encrypt.encrypt(string);
    return encryptResult;
}

/*解密*/
function decrypt(string) {
    //密文
    var ttt="..........................";
    //设置解密私钥
    encrypt.setPrivateKey(".......")
    //分段解密
    var sss= encrypt.decryptLong2(sss);
}

function hexToBytes(hex) {
    for (var bytes = [], c = 0; c < hex.length; c += 2)
        bytes.push(parseInt(hex.substr(c, 2), 16));
    return bytes;
}

// Convert a byte array to a hex string
function bytesToHex(bytes) {
    for (var hex = [], i = 0; i < bytes.length; i++) {
        hex.push((bytes[i] >>> 4).toString(16));
        hex.push((bytes[i] & 0xF).toString(16));
    }
    return hex.join("");
}

