const mysql = require("mysql2");
const config = require("./config.js");

const pool = mysql.createPool({
  connectionLimit: 10,
  host: "localhost",
  user: "root",
  password: "root",
});

pool.getConnection(function (err, connection) {
  if (err) throw err; // not connected!

  connection.connect((err) => {
    if (err) {
      console.log("链接失败");
    } else {
      console.log("连接成功");
    }
  });
});
module.exports = pool.promise();
