function getEncryptPassword(modulus,exponent,password )      { //注意，参数不要带var。。在java里执行会报错。。

      var rsaKey = new RSAKey();
      rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));
      var enPassword = hex2b64(rsaKey.encrypt(password));
      return enPassword;

}