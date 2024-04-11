const crypto = require("crypto");
/**
 * 生成 RSA 公私钥对
 * @return {Object} { publicKey, privateKey }
 */
const generateRSAKeyPair = () => {
  const { publicKey, privateKey } = crypto.generateKeyPairSync("rsa", {
    modulusLength: 2048, // 秘钥长度

    // 秘钥配置，详见 https://nodejs.cn/dist/latest-v18.x/docs/api/crypto.html#keyobjectexportoptions
    publicKeyEncoding: {
      type: "spki", // 编码类型
      format: "pem", // 编码格式
    },
    privateKeyEncoding: {
      type: "pkcs8",
      format: "pem",
    },
  });
  return { publicKey, privateKey };
};

/**
 * 使用公钥进行加密，加密出错会抛出异常
 * @param {any} data 需要加密的数据，会使用 JSON.stringify 序列化
 * @param {string} publicKey
 * @return {string} 加密后的密文
 */
const encrypt = (data, publicKey) => {
  const dataJSON = JSON.stringify(data);
  return crypto
    .publicEncrypt(publicKey, Buffer.from(dataJSON, "utf-8"))
    .toString("base64");
};

/**
 * 使用私钥进行解密，解密出错会抛出异常
 * @param {string} secretText 密文
 * @param {string} privateKey 私钥
 * @return {String} 解密后的明文，会使用 JSON.parse 反序列化
 */
const decrypt = (secretText, privateKey) => {
  const dataStr = crypto
    .privateDecrypt(
      {
        key: privateKey,

        // padding 的值需要与公钥的编码类型相对应
        padding: crypto.constants.RSA_PKCS1_PADDING,
      },
      Buffer.from(secretText, "base64")
    )
    .toString("utf-8");
  return dataStr;
};
module.exports = {
  generateRSAKeyPair,
  encrypt,
  decrypt,
};
