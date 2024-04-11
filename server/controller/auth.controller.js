const jwt = require("jsonwebtoken");

const authService = require("../service/auth.service");
const { PRIVATE_KEY } = require("../app/config");
class authController {
  async login(ctx, next) {
    const { id, userName } = ctx.user;

    const token = jwt.sign({ id, userName }, PRIVATE_KEY, {
      //24h后失效
      expiresIn: 60 * 60 * 24,
      //非对称加密
      algorithm: "RS256",
    });
    ctx.body = { id, userName, token };
  }
  async success(ctx, next) {
    ctx.body = "授权成功";
  }
}

module.exports = new authController();
