var express = require("express");
const captcha = require("../utils/Code");
const { setString } = require("../utils/redis-code.js");
const {
  verifyUsers,
  handlePassword,
} = require("../middleware/user.middleware.js");
const { verifyLogin, verifyAuth } = require("../middleware/auth.middleware.js");
const UserController = require("../controller/user.controller.js");
const AuthController = require("../controller/auth.controller.js");

const { PUBLIC_KEY } = require("../app/config.js");
var router = express.Router();

/**
 * 用户注册
 * 首先验证用户名和密码是否有效，比如是否为空，现有的数据库是否已有相同的用户名
 * 数据有效后，对密码进行md5加密，放入数据库中创建用户
 */
router.get("/getPublicKey", function (req, res, next) {
  /**
   * 将公钥发送给前端加密密码
   */
  res.status(200).send({ data: PUBLIC_KEY, code: 200 });
});
/**
 * 用户登录
 * 取出表单数据查询数据库中是否有该用户，密码是否正确
 * 如果正确的话，将token传给客户端
 */
router.post("/register", [verifyUsers, handlePassword, UserController.create]);
/**
 * 用户登录
 * 取出表单数据查询数据库中是否有该用户，密码是否正确
 * 如果正确的话，将token传给客户端
 */
router.post("/login", [verifyLogin, AuthController.login]);

router.get("/getSecurityCode", function (req, res, next) {
  let code = captcha();
  setString(req.sessionID, code.text.toLowerCase(), 300);
  res.type("html"); //res.type('svg'); // 使用ejs等模板时报错
  res.status(200).send({ data: code.data, code: 200 });
});

module.exports = router;
