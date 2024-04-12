const jwt = require("jsonwebtoken");

const authService = require("../service/auth.service");
const { PRIVATE_KEY } = require("../app/config");
class authController {
  async login(req, res, next) {
    const { id, username } = req.user;

    const token = jwt.sign({ id, username }, PRIVATE_KEY, {
      //24h后失效
      expiresIn: 60 * 60 * 24,
      //非对称加密
      algorithm: "RS256",
    });
    res.setHeader("authorization", token);
    res.status(200).send({ user: { id, username }, code: 200 });
    console.log("res", res);
  }
  async success(req, res, next) {
    res.body = "授权成功";
  }
}

module.exports = new authController();
