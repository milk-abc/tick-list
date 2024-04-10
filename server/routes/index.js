var express = require("express");
const captcha = require("../utils/Code");
const { setString } = require("../utils/redis-code.js");
// const {
//   verifyUsers,
//   handlePassword,
// } = require("../middleware/user.middleware.js");
// const UserController = require("../controller/user.controller.js");
// const { PUBLIC_KEY } = require("../app/config.js");
var router = express.Router();

/**
 * 用户注册
 * 首先验证用户名和密码是否有效，比如是否为空，现有的数据库是否已有相同的用户名
 * 数据有效后，对密码进行md5加密，放入数据库中创建用户
 */
// router.post("/register", [verifyUsers, handlePassword, UserController.create]);
// router.get("/getPublicKey", function (req, res, next) {
//   /**
//    * 用户登录
//    * 取出表单数据查询数据库中是否有该用户，密码是否正确
//    * 如果正确的话，将token传给客户端
//    */
//   console.log("hhhhhhh");
//   res.status(200).send({ code: 200 });
// });
// /* GET home page. */
router.get("/login", function (req, res, next) {
  /**
   * 用户登录
   * 取出表单数据查询数据库中是否有该用户，密码是否正确
   * 如果正确的话，将token传给客户端
   */
  console.log("hhhhhhhhhhhhhhhh");
  res.render("index", { title: "Express" });
});
// router.post("/register", function (req, res, next) {
//   /**
//    * 用户登录
//    * 取出表单数据查询数据库中是否有该用户，密码是否正确
//    * 如果正确的话，将token传给客户端
//    */
//   console.log("hhhhhhh");
// });
router.get("/getSecurityCode", function (req, res, next) {
  console.log("code");
  let code = captcha();
  setString(req.sessionID, code.text.toLowerCase(), 300);
  res.type("svg");
  res.status(200).send({ data: code.data, code: 200 });
  res.end();
});

module.exports = router;
