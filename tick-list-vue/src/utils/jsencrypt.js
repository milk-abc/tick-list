import JSEncrypt from "jsencrypt/bin/jsencrypt";
//加密
export function encrypt(txt, publicKey) {
  const encryptor = new JSEncrypt();
  encryptor.setPublicKey(publicKey); //设置公钥
  return encryptor.encrypt(txt);
}
//解密
export function decrypt(txt, privateKey) {
  const encryptor = new JSEncrypt();
  encryptor.setPrivateKey(privateKey); //设置公钥
  return encryptor.decrypt(txt);
}
