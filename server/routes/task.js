var express = require("express");

var router = express.Router();
const {
  getDayData,
  getWeekData,
  saveTaskData,
} = require("../service/task.service");

/* GET users listing. */
router.get("/countTaskForDay/:userId", async function (req, res, next) {
  const userId = req.params.userId;
  const result = await getDayData(userId);
  console.log("result", result);
  res.status(200).send({ msg: "操作成功", code: 200 });
});

router.get("/countTaskForWeek/:userId", async function (req, res, next) {
  const userId = req.params.userId;
  const result = await getWeekData(userId);
  console.log("result", result);
  res.status(200).send({ msg: "操作成功", code: 200 });
});

router.post("/addList", async function (req, res, next) {
  const result = await saveTaskData();
  console.log("result", result);
  res.status(200).send({ msg: "操作成功", code: 200 });
});

module.exports = router;
