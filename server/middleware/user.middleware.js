const { PRIVATE_KEY } = require("../app/config");
const errTypes = require("../constants/error-types");
const userService = require("../service/user.service");
const md5password = require("../utils/password-handle");
const { decrypt } = require("../utils/jsencrypt");
const crypto = require("crypto");
const verifyUsers = async (req, res, next) => {
  const { username, password } = req.body;
  if (!username || !password) {
    const error = new Error(errTypes.NAME_OR_PASSWORD_IS_REQUIRED);
    return next(error);
  }
  const result = await userService.getUserByName(username);
  if (result.length) {
    const error = new Error(errTypes.USER_ALREADY_EXISTS);
    return next(error);
  }
  await next();
};
const handlePassword = async (req, res, next) => {
  const { password } = req.body;
  const initialPassword = decrypt(password, PRIVATE_KEY);
  req.body.password = md5password(initialPassword);
  await next();
};
module.exports = { verifyUsers, handlePassword };
