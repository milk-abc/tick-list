const connect = require("../app/dataBase.js");
const moment = require("moment");
const {
  en_tomorrow,
  en_today,
  days_ago,
  en_format,
} = require("../utils/formatDate.js");
class TaskService {
  async create(user) {
    const { username, password } = user;
    const statement = `insert into user (username,password) values (?,?);`;
    const result = await connect.execute(statement, [username, password]);
    return result[0];
  }
  async getDayData(userId) {
    const statement = `select DATE_FORMAT(update_time,'%Y-%m-%d') days,count(id) count from task 
    where update_time > DATE_SUB(CURDATE(),INTERVAL 7 Day)
    and user_id = ? and run = 0
    group by days;`;
    const totalStatement = `select DATE_FORMAT(update_time,'%Y-%m-%d') days,count(id) count from task 
    where update_time > DATE_SUB(CURDATE(),INTERVAL 7 Day)
    and user_id = ? group by days;`;
    const data = [];
    for (let i = 0; i < 7; i++) {
      data.unshift(en_format(days_ago(i)));
    }
    const midResult = await connect.execute(statement, [userId]);
    const totalResult = await connect.execute(totalStatement, [userId]);
    console.log("midResult", midResult[0], "total", totalResult[0]);
    const result = [];
    for (let i = 0; i < 7; i++) {
      result.push({ date: data[i], day: 0, total: 0 });
    }
    for (let i = 0; i < 7; i++) {
      for (const item of midResult[0]) {
        if (item.days == data[i]) {
          result[i]["day"] = item.count;
        }
      }
    }
    for (let i = 0; i < 7; i++) {
      for (const item of totalResult[0]) {
        if (item.days == data[i]) {
          result[i]["total"] = item.count;
        }
      }
    }

    return result;
  }
  async getWeekData(userId) {
    const time = 7;
    const data = [];
    //获取本周一的日期
    const now = new Date();
    const day = now.getDay();
    let monday;
    if (day === 0) {
      //如果是周日
      monday = days_ago(6);
    } else if (day === 1) {
      monday = days_ago(0);
    } else {
      monday = days_ago(day - 1);
    }
    const toDay = en_format(days_ago(-1));
    const latestStatement = `select count(id) count from task 
      where update_time >= ? and update_time < ?
      and user_id = ? and run = 0;`;
    const latestResult = await connect.execute(latestStatement, [
      en_format(monday),
      toDay,
      userId,
    ]);
    data.unshift({
      date: en_format(days_ago(0)),
      week: latestResult[0][0].count || 0,
    });
    for (let i = 0; i < time - 1; i++) {
      const curDay = en_format(days_ago(i * 7, monday));
      const weekAgo = en_format(days_ago((i + 1) * 7, monday));
      const statement = `select count(id) count from task 
      where update_time >= ? and update_time < ?
      and user_id = ? and run = 0;`;
      const midResult = await connect.execute(statement, [
        weekAgo,
        curDay,
        userId,
      ]);
      data.unshift({
        date: en_format(days_ago(i * 7, monday)),
        week: midResult[0][0].count || 0,
      });
    }
    return data;
  }
  async saveTaskData({
    id,
    user_id,
    category_id,
    name,
    labelList,
    description,
  }) {
    if (id != 0) {
      const updateStatement = `update task set category_id = ?, name = ?, description = ?, update_time = now()
      where id = ?;`;
      const deleteStatement = `delete from task_label where task_id = ?;`;
      await connect.execute(updateStatement, [
        category_id,
        name,
        description,
        id,
      ]);
      await connect.execute(deleteStatement, [id]);
    }
    const insertTaskStatement = `INSERT INTO task ( user_id, category_id, name, description, create_time, update_time, run ) VALUES ( ?, ?, ?, ?, NOW(), NOW(), 1 ); `;
    await connect.execute(insertTaskStatement, [
      user_id,
      category_id,
      name,
      description,
    ]);
    const taskIdResult = await connect.execute(
      `SELECT LAST_INSERT_ID() as taskId;`
    );
    for (let item of labelList) {
      /**
       * 怎么获取task_id
       */
      const insertRelationStatement = `INSERT INTO task_label (task_id, label_id, create_time, update_time) VALUES (?, ?, NOW(), NOW() );`;
      await connect.execute(insertRelationStatement, [
        taskIdResult[0][0].taskId,
        item,
      ]);
    }
  }
  async finishTaskData(taskId) {
    const statement = `UPDATE task SET update_time = NOW(), run = 0 WHERE id = ?;`;
    await connect.execute(statement, [taskId]);
  }
  async getTaskPageListData(userId, pageCurrent, pageSize, body) {
    const startOrder = (pageCurrent - 1) * pageSize;
    const paramsList = [userId, 1];
    let statement = `SELECT  task.id,task.user_id,task.category_id,task.name,task.description,task.create_time,task.update_time,task.run  FROM task left join task_label on task.id = task_label.task_id WHERE user_id = ? AND run = ? `;
    let totalStatement = `SELECT count(task.id) as 'total' FROM task left join task_label on task.id = task_label.task_id WHERE user_id = ? AND run = ? `;
    const statement2 = `limit ?, ?;`;
    let statement3 = "",
      statement4 = "";

    if (body?.name) {
      statement3 = statement3 + ` AND name = ? `;
      paramsList.push(body?.name);
    }
    if (body?.categoryId) {
      statement3 = statement3 + ` AND category_id = ? `;
      paramsList.push(body?.categoryId);
    }

    if (body?.labelIdList?.length) {
      statement4 = ` AND label_id IN (?`;
      paramsList.push(...body?.labelIdList);
      for (let i = 1; i < body?.labelIdList.length; i++) {
        statement4 += `,?`;
      }
      statement4 += `) `;
    }
    statement = statement + statement3 + statement4 + statement2;
    paramsList.push(`${startOrder}`, pageSize);
    const dataResult = await connect.execute(statement, paramsList);
    totalStatement = totalStatement + statement3 + statement4;
    const totalResult = await connect.execute(
      totalStatement,
      paramsList.slice(0, paramsList.length - 2)
    );
    console.log("totalResult", totalResult);
    const result = Object.assign(
      {},
      { records: dataResult[0] },
      totalResult[0][0]
    );
    return result;
  }
  async countTodayFinishCategory(userId) {
    const statement = `select ca.name as categoryName, count(*) as count
    from category ca inner join task ta on ca.id = ta.category_id
    where ta.run = 0 and ta.update_time > CURDATE() and ta.user_id = ?
    group by ca.name ;`;
    const result = await connect.execute(statement, [userId]);
    return result[0];
  }
}
module.exports = new TaskService();
