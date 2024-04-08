const code = require("svg-captcha");
function createCode() {
  return code.create({
    size: 4,
    ignoreChars: "0o1iIl",
    noise: 3,
    color: true,
    background: "#fff",
    fontSize: 60,
  });
}
module.exports = createCode;
