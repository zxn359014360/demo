/*加密*/
function encrypt(string) {
    var APublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp4KJIKurc/VeI7h0MrfGxzsceSiVtB3uZJi8ZR2mqFi2/LxI1M326p2SOllKlR5D5mMnjNWE5tY9PsjpkpI3J0OSrd3N4o9WOJHmZsTqEklS9RKwxrxrCN6AuEQgfUuCNRMNvexFgUpKJZuDfpS4jQkrReC5rfNCARl/GNS+UtmXfT2PTEnM4Mz42W4HZnjIl2O1NbkQacafsG5xQkHm222uMmT4kU2gxb64eANAYD1HiucB2ezQsZ82FxgyZ1DetB+aoy3tVkUgz5gCerlcdEvqJlkn2rVGEJqiWsiiEE4Ab57I2OXFOXqBwIex8jPX0ZrsnllFZSZsSUiCHv3yJwIDAQAB";

    if(APublicKey == "" || APublicKey == null || APublicKey == "undefind"){
        console.log("公钥为空")
    }
    //实例化加密对象
    var encrypt = new JSEncrypt();
    //设置加密公钥
    encrypt.setPublicKey(APublicKey);

    //分段加密
    var encryptResult =encrypt.encrypt(string);
    return encryptResult;
}

/*解密*/
function decrypt(string) {
    var BPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfQKVBlRHk09e3quFquox7eaCLq/qW+P/skZa8W28cL9EZDNgjE1cyuouULmN46fY+NyW80XIkqCuV+xCVKH1qkxJj4pqSn+2fTXEJlhrjgc0tT9q5O4gZk39ef4VSKvpTtCLPCdPfTY3JUVvssiMMJ+HfDNWCZMJxHt1IHp1bRLNLxKOBflovy5B+iyJBOL3sdKhgr76YPV/eeghsTveVpoEzytF1jFJPD/Uzc76G2m2EF6hCBFfmHb8nYp1BMuhOEt0C2OuMb2SwWNnn21drHNFOO1vtjvxuykTwVxqWxowROQIpzsclDCEsJOnYgRM/5//26rW3kCDJu7EpkQkHAgMBAAECggEADEOXNmi7ewrRZwSS3XxGS0//jCIPZ0b/toEzwYetE9ee27YAgxr/MRnqm9vCr1IdM4ddsZ/TkX0d9iviiAoZNnbjCKwvyTDlxMna9akIoxKg8Wdf6bnq7kVMnsNlSgGjS4To1VblaSH41FMeqCjDyDDeFSZQ3vzReJKu6dnptliB2lQdJxaoQic7qX/s2G/OcSut5M6CS0hax7mbt4ixvn1WZKDuTGy7UX87eS7zzHYgHIxfrjVdv2vpAki1c6dA+fs65v+ycJ8b1FD+QR6RqyW2loS6Kom4BZa+6b3HhbaQhFYIv/J8/tBnBUw0jGUOOMZ9R32upJuI9VOQsvoVyQKBgQDcaW4JSwIG9MqpRsIid8THIckaurniMxb+E4nfSIhw7VKry+W6Wq9LRlhqI+Hucj+qepYoaxnPtq1An2lRj3K6oCNQrulX0tpAJ8yt7AK4Ih+8VCAmc7tK4IiIZTQlsAtAfk8BsmRgNFxnaMviRnhCWlQeegTxrucjoTgFZ1EWcwKBgQC49zzpe6GJ0XluTq6oBHYzth7+/W//TaHWzp8IKZZ6HpwewNjRpZb95GwEkpDmlKI9ZeYfYpsADhS20vT/Y2X17DaeBMb56oCtqthuOPoCQq33mLOl6nAybYBarHov6wnnjYJrsj4SrHGMIJ3EbudBtrlczAcViARdCDpS6jsKHQKBgQCtPJhl6XSAbypSffDcEnKxaNadkaHELg+mR0kYG/RRfdZ6fNnk7J3WdkXLtsQ/G8D6hzs6LWOuJJBruF+Y4Tx1fqQDKFby1iEAR8fR8yv3+aoj5aMXVeMhaPUm7xRbgtB1MlF6rfHCw6TVCkMlZ+nhjQmMoq78HlJBaNeVN60+DQKBgQCwK+RbakLI9riXBOA77cv7oupQ6mlDHNkZtMEqUBogBImkjP+2Zuayul7b2BGlXUpN+oE9wXhqi/7Ux4dMvIq/uhWnWlt+bAnV39S23xPZFVOHcW0iiXIFxeAr/P2AKHjNkC35j9KpjI+17Nb34r3nDDfvF/FjY6LsTUrn8Gr9TQKBgHkiAquoF/3qyVsnGcC/5NvwK9/DnwBZ2KWfd5K2h1Pwo81XPrRzAATrWc63mqImKsGK6uPnca1JLkzKy6wE9nES1un1j+kFQTIE3bRotfvvltk9nIGZJN9T7E16yobwOGV9OcheiwFQqment0nESLwGthjc3n+VsCtBqSgMRQ0f";

    //设置解密私钥
    encrypt.setPrivateKey(BPrivateKey)
    //分段解密
    return encrypt.decryptLong2(string);
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

