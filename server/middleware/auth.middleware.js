const jwt = require("jsonwebtoken");
const { PUBLIC_KEY, PRIVATE_KEY } = require("../app/config");

const errTypes = require("../constants/error-types");
const service = require("../service/user.service");
const md5password = require("../utils/password-handle");
const { decrypt } = require("../utils/jsencrypt");
const verifyLogin = async (req, res, next) => {
  //1.获取用户名和密码
  const { username, password } = req.body;

  //2.判断用户名和密码是否为空
  if (!username || !password) {
    const error = new Error(errTypes.NAME_OR_PASSWORD_IS_REQUIRED);
    return next(error);
  }
  // 3.判断用户是否存在
  const result = await service.getUserByName(username);
  const user = result[0];
  if (!user) {
    const error = new Error(errTypes.USER_DOES_NOT_EXISTS);
    return next(error);
  }
  const initialPassword = decrypt(password, PRIVATE_KEY);
  // 4.判断密码是否和数据库中的密码是一致(加密)
  if (md5password(initialPassword) !== user.password) {
    const error = new Error(errTypes.PASSWORD_IS_INCORRENT);
    return next(error);
  }
  //将user信息放入ctx，以便后面的中间件处理
  req.user = user;
  await next();
};

const verifyAuth = async (req,res, next) => {
  console.log("验证登陆的授权");
  //获取token
  const authorization = req.headers.authorization;

  if (!authorization) {
    const error = new Error(errTypes.UNAUTHORIZATION);
    return next(error);
  }
  //因为是postman发送过来的，因此会多出Bearer这个字符
  const token = authorization.replace("Bearer ", "");
  //验证token

  try {
    const result = jwt.verify(token, PUBLIC_KEY, {
      algorithms: ["RS256"],
    });
    req.user = result;
    await next();
  } catch (error) {
    const err = new Error(errTypes.UNAUTHORIZATION);
    return next(error);
  }
};

module.exports = {
  verifyLogin,
  verifyAuth,
};
