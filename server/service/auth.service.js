const connect = require("../app/dataBase.js");

class authService {
  async create(user) {
    const { userName, password } = user;

    const statement = `insert into user (name,password) values (?,?)`;

    const result = await connect.execute(statement, [userName, password]);
    return result[0];
  }
  async getUserByName(userName) {
    const statement = `SELECT * FROM user WHERE name = ?;`;
    const result = await connect.execute(statement, [userName]);
    return result[0];
  }
}

module.exports = new authService();
