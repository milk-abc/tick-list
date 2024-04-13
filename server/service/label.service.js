const connect = require("../app/dataBase.js");
const moment = require("moment");
const { en_tomorrow, en_today } = require("../utils/formatDate.js");
class LabelService {
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
  async getListAllData(userId) {
    const statement = `SELECT id, name FROM label WHERE user_id = ?`;
    const result = await connect.execute(statement, [userId]);
    return result[0];
  }
}
module.exports = new LabelService();
