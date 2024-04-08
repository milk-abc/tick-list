var express = require("express");
const captcha = require("../utils/Code");
const { setString } = require("../utils/redis-code");
var router = express.Router();

/* GET home page. */
router.get("/login", function (req, res, next) {
  res.render("index", { title: "Express" });
});

router.get("/getSecurityCode", function (req, res, next) {
  console.log("code");
  let code = captcha();
  setString(req.sessionID, code.text.toLowerCase(), 300);
  res.type("svg");
  res.status(200).send({ data: code.data, code: 200 });
  res.end();
});

module.exports = router;
