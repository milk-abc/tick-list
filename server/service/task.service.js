const connect = require("../app/dataBase.js");
const moment = require("moment");
const { en_tomorrow, en_today } = require("../utils/formatDate.js");
class TaskService {
  async create(user) {
    const { username, password } = user;
    const statement = `insert into user (username,password) values (?,?);`;
    const result = await connect.execute(statement, [username, password]);
    return result[0];
  }
  async getDayData(userId) {
    const statement = `SELECT COUNT( 1 ) FROM task WHERE (user_id = ? AND update_time BETWEEN ? AND ? AND update_time >= ?);`;
    // const startTime = new Date(new Date().setHours(0, 0, 0));
    // const endTime = new Date(new Date().setHours(23, 59, 59));
    const startTime = new Date(
      new Date(new Date().toLocaleDateString()).getTime() - 7 * 24 * 3600 * 1000
    );
    const endTime = new Date();
    const result = await connect.execute(statement, [
      userId,
      startTime,
      endTime,
      startTime,
    ]);
    return result[0];
  }
  async getWeekData(userId) {
    const statement = `SELECT COUNT( 1 ) FROM task WHERE (user_id = ? AND update_time BETWEEN ? AND ? AND update_time >= ?);`;
    // const startTime = new Date(new Date().setHours(0, 0, 0));
    // const endTime = new Date(new Date().setHours(23, 59, 59));
    const startTime = new Date(
      new Date(new Date().toLocaleDateString()).getTime() -
        49 * 24 * 3600 * 1000
    );
    const endTime = new Date();
    const result = await connect.execute(statement, [
      userId,
      startTime,
      endTime,
      startTime,
    ]);
    return result[0];
  }
  async saveTaskData(userId) {
    const deleteStatement1 = `DELETE FROM task_label WHERE (task_id = 191) ;`;
    const deleteStatement2 = `DELETE FROM task WHERE (id = 191) ;`;
    const insertTaskStatement = `INSERT INTO task ( user_id, category_id, name, description, create_time, update_time ) VALUES ( 23, 157, "react", "", NOW(), NOW() );`;
    const insertRelationStatement = `INSERT INTO task_label (task_id, label_id, create_time, update_time) VALUES (191, 1, NOW(), NOW() );`;
    const startTime = new Date(
      new Date(new Date().toLocaleDateString()).getTime() -
        49 * 24 * 3600 * 1000
    );
    const endTime = new Date();
    await connect.execute(deleteStatement1);
    await connect.execute(deleteStatement2);
    await connect.execute(insertTaskStatement);
    await connect.execute(insertRelationStatement);
  }
}
module.exports = new TaskService();
