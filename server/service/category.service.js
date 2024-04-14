const connect = require("../app/dataBase.js");
const moment = require("moment");
const { en_tomorrow, en_today } = require("../utils/formatDate.js");
class CategoryService {
  async addCategoryData(userId, name) {
    const statement = `INSERT INTO category (user_id,name,create_time,update_time,run) VALUES (?,?,NOW(),NOW(),1);`;
    await connect.execute(statement, [userId, name]);
    const categoryIdResult = await connect.execute(
      `SELECT LAST_INSERT_ID() as categoryId;`
    );
    return categoryIdResult[0][0].categoryId;
  }
  async getCategoryPageListData(userId, pageCurrent, pageSize) {
    const statement = `SELECT ca.id, ca.name, count(ta.id) as 'taskCount'
    FROM category ca left join task ta on ca.id = ta.category_id 
    WHERE (ca.user_id = ?)
    group by ca.id, ca.name LIMIT ?,?;`;
    const startOrder = (pageCurrent - 1) * pageSize;
    //分页接收的是字符串类型
    const dataResult = await connect.execute(statement, [
      userId,
      `${startOrder}`,
      pageSize,
    ]);
    const statement2 = `SELECT count(ca.id) as 'total'
    FROM category ca WHERE (ca.user_id = ?);`;
    const totalResult = await connect.execute(statement2, [userId]);
    const result = Object.assign(
      {},
      { records: dataResult[0] },
      totalResult[0][0]
    );
    return result;
  }
  async getListAllData(userId) {
    const statement = `SELECT id,name FROM category WHERE user_id = ?`;
    const result = await connect.execute(statement, [userId]);
    return result[0];
  }
}

module.exports = new CategoryService();
