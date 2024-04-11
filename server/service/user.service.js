const connect = require("../app/dataBase.js");
class UserService {
  async create(user) {
    const { username, password } = user;
    const statement = `insert into user (username,password) values (?,?);`;
    const result = await connect.execute(statement, [username, password]);
    return result[0];
  }
  async getUserByName(userName) {
    const statement = `SELECT * FROM user WHERE username = ?;`;
    const result = await connect.execute(statement, [userName]);
    return result[0];
  }
}
module.exports = new UserService();
