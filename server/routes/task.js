var express = require("express");

var router = express.Router();
const {
  getDayData,
  getWeekData,
  saveTaskData,
  finishTaskData,
  getTaskPageListData,
  countTodayFinishCategory,
  countCategoryAndLabelForDay,
  getStatistics,
} = require("../service/task.service");

/* GET users listing. */
router.get("/countTaskForDay/:userId", async function (req, res, next) {
  const userId = req.params.userId;
  const result = await getDayData(userId);
  console.log("result1", result);
  res.status(200).send({ msg: "操作成功", code: 200, data: [...result] });
});

router.get("/countTaskForWeek/:userId", async function (req, res, next) {
  const userId = req.params.userId;
  const result = await getWeekData(userId);
  console.log("result2", result);
  res.status(200).send({ msg: "操作成功", code: 200, data: [...result] });
});

router.post("/addList", async function (req, res, next) {
  for (let item of req.body) {
    await saveTaskData(item);
  }
  res.status(200).send({ msg: "操作成功", code: 200 });
});

router.get("/delete/:taskId", async function (req, res, next) {
  const { taskId } = req.params;
  await finishTaskData(taskId);
  res.status(200).send({ msg: "操作成功", code: 200 });
});

router.get("/countTodayForCategory/:userId", async function (req, res, next) {
  const { userId } = req.params;
  const result = await countTodayFinishCategory(userId);
  res.status(200).send({ msg: "操作成功", code: 200, data: [...result] });
});

router.get(
  "/statistics/countCategoryAndLabelForDay/:userId",
  async function (req, res, next) {
    const { userId } = req.params;
    const result = await countCategoryAndLabelForDay(userId);
    console.log("result", result);
    res.status(200).send({ msg: "操作成功", code: 200, data: [...result] });
  }
);

router.get("/getStatistics/:userId", async function (req, res, next) {
  const { userId } = req.params;
  const result = await getStatistics(userId);
  console.log("result", result);
  res.status(200).send({ msg: "操作成功", code: 200, data: result });
});

router.post(
  "/getPageList/:userId/:pageCurrent/:pageSize",
  async function (req, res, next) {
    const { userId, pageCurrent, pageSize } = req.params;

    const result = await getTaskPageListData(
      userId,
      pageCurrent,
      pageSize,
      req.body
    );
    console.log("result", result);
    const { records, total } = result;
    res.status(200).send({
      msg: "操作成功",
      code: 200,
      data: {
        records,
        total,
        size: Number(pageSize),
        current: Number(pageCurrent),
        pages: Math.ceil(total / Number(pageSize)),
      },
    });
  }
);

module.exports = router;
