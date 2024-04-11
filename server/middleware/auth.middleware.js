const jwt = require("jsonwebtoken");
const { PUBLIC_KEY } = require("../app/config");

const errTypes = require("../constants/error-types");
const service = require("../service/userService");
const md5password = require("../utils/password-handle");
const verifyLogin = async (ctx, next) => {
  //1.获取用户名和密码
  const { userName, password } = ctx.request.body;

  //2.判断用户名和密码是否为空
  if (!userName || !password) {
    const error = new Error(errTypes.NAME_OR_PASSWORD_IS_REQUIRED);
    return ctx.app.emit("error", error, ctx);
  }
  // 3.判断用户是否存在
  const result = await service.getUserByName(userName);
  const user = result[0];
  if (!user) {
    const error = new Error(errTypes.USER_DOES_NOT_EXISTS);
    return ctx.app.emit("error", error, ctx);
  }

  // 4.判断密码是否和数据库中的密码是一致(加密)
  if (md5password(password) !== user.password) {
    const error = new Error(errTypes.PASSWORD_IS_INCORRENT);
    return ctx.app.emit("error", error, ctx);
  }
  //将user信息放入ctx，一边后面的中间件处理
  ctx.user = user;
  await next();
};

const verifyAuth = async (ctx, next) => {
  console.log("验证登陆的授权");
  //获取token
  const authorization = ctx.headers.authorization;

  if (!authorization) {
    const error = new Error(errTypes.UNAUTHORIZATION);
    return ctx.app.emit("error", error, ctx);
  }
  //因为是postman发送过来的，因此会多出Bearer这个字符
  const token = authorization.replace("Bearer ", "");
  //验证token

  try {
    const result = jwt.verify(token, PUBLIC_KEY, {
      algorithms: ["RS256"],
    });
    ctx.user = result;
    await next();
  } catch (error) {
    const err = new Error(errTypes.UNAUTHORIZATION);
    return ctx.app.emit("error", err, ctx);
  }
};

module.exports = {
  verifyLogin,
  verifyAuth,
};
