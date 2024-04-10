const errTypes = require("../constants/error-types");
const userService = require("../service/user.service");
const md5password = require("../utils/password-handle");
const verifyUsers = async (req, res, next) => {
  console.log("hhhhhhhhhhhhhhh");
  const { name, password } = req.body;
  if (!name || !password) {
    const error = new Error(errTypes.NAME_OR_PASSWORD_IS_REQUIRED);
    return next(error);
  }
  const result = await userService.getUserByName(name);
  console.log("result", result);
  if (result.length) {
    const error = new Error(errTypes.USER_ALREADY_EXISTS);
    return next(error);
  }
  await next();
};
const handlePassword = async (req, res, next) => {
  const { password } = req.body;
  req.body.password = md5password(password);
  await next();
};
module.exports = { verifyUsers, handlePassword };
