const redis = require("redis");
const client = redis.createClient({
  host: "127.0.0.1", //	redis地址
  port: 3000, // 端口号
});
// 监听连接事件
client.on("connect", (error) => {
  if (!error) {
    console.log("connect to redis");
  }
});
// 监听错误事件
client.on("error", (error) => {
  throw new Error(error);
});

function setString(key, value, expire) {
  return new Promise((resolve, reject) => {
    client.set(key, value, (error, replay) => {
      if (error) {
        reject("设置失败");
      }
      if (expire) {
        client.expire(key, expire);
      }
      resolve("设置成功");
    });
  });
}
function getString(key) {
  return new Promise((resolve, reject) => {
    if (key) {
      client.get(key, (error, replay) => {
        if (error) {
          reject(`获取${key}失败`);
        }
        resolve(replay);
      });
    }
  });
}

module.exports = {
  setString,
  getString,
};
