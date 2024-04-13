const connect = require("../app/dataBase.js");
const moment = require("moment");
const { en_tomorrow, en_today } = require("../utils/formatDate.js");
class LabelService {
  async create(user) {
    const { username, password } = user;
    const statement = `insert into user (username,password) values (?,?);`;
    const result = await connect.execute(statement, [username, password]);
    return result[0];
  }
  async getData(userId) {
    const statement = `SELECT id,user_id,name,create_time,update_time FROM category WHERE 
		(user_id = ?);`;
    const result = await connect.execute(statement, [userId]);
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
  async addCategoryData(userId, name) {
    const statement = `INSERT INTO category (user_id,name,create_time,update_time,run) VALUES (?,?,NOW(),NOW(),1);`;
    const result = await connect.execute(statement, [userId, name]);
    console.log(result);
  }
  async getLabelPageListData(userId, pageCurrent, pageSize) {
    const statement = `select la.id, la.name, count(la.id) as 'taskCount'
    from label la inner join task ta on la.id = ta.category_id 
    where (la.user_id = ?)
    group by la.id, la.name LIMIT ?,?;`;
    const startOrder = (pageCurrent - 1) * pageSize;
    //分页接收的是字符串类型
    const result = await connect.execute(statement, [
      userId,
      `${startOrder}`,
      pageSize,
    ]);
    return result[0];
  }
	
}
module.exports = new LabelService();
