const connect = require("../app/dataBase.js");
const moment = require("moment");
const { en_tomorrow, en_today } = require("../utils/formatDate.js");
class LabelService {
  async getLabelPageListData(userId, pageCurrent, pageSize) {
    const statement = `select la.id, la.name, count(tl.task_id) as 'taskCount'
    from label la left join task_label tl on la.id = tl.label_id 
    where (la.user_id = ?)
    group by la.id, la.name LIMIT ?,?;`;
    const startOrder = (pageCurrent - 1) * pageSize;
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
  async addLabel(userId, name) {
    const statement = `INSERT INTO label (name, user_id) VALUES (?, ?);`;
    await connect.execute(statement, [name, userId]);
    const labelIdResult = await connect.execute(
      `SELECT LAST_INSERT_ID() as labelId;`
    );
    return labelIdResult[0][0].labelId;
  }
}
module.exports = new LabelService();
