const mysql = require("mysql2");
const config = require("./config.js");

const pool = mysql.createPool({
  connectionLimit: 10,
  host: "localhost",
  user: "root",
  password: "root",
  database: "our-task",
  port: 3306,
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
// const mysql = require("mysql2/promise");
// const config = require("./config.js");

// async function main() {
//   try {
//     const connection = await mysql.createConnection({
//       host: "localhost",
//       user: "root",
//       password: "root",
//       database: "our-task",
//       port: 3306,
//     });
//     await connection.connect();
//     console.log("链接成功");
//     // 执行查询
//     const [rows, fields] = await connection.query("SELECT * FROM user");
//     console.log(rows);
//     await connection.end();
//   } catch (err) {
//     console.log("链接失败", err);
//   }
// }
// main();
