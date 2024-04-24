# 清单

## 添加一个新的清单

往清单表里添加一条数据

```
INSERT INTO task ( user_id, category_id, name, description, create_time, update_time ) VALUES ( ?, ?, ?, ?, ?, ? ) 
==> Parameters: 1(Integer), 1(Integer), 我今天的第一个清单(String), 我今天的第一个清单(String), 2024-04-13 18:27:28.442(Timestamp), 2024-04-13 18:27:28.442(Timestamp)
```

往清单标签表里添加一条数据

```
INSERT INTO task_label ( task_id, label_id, create_time, update_time ) VALUES ( ?, ?, ?, ? ) 
==> Parameters: 182(Integer), 2(Integer), 2024-04-13 18:27:28.464(Timestamp), 2024-04-13 18:27:28.464(Timestamp)
```

## 逻辑删除一个清单

逻辑删除清单表的那条数据

```
UPDATE task SET update_time=?, run=? WHERE id=? 
==> Parameters: 2024-04-13 18:40:30.654(Timestamp), 0(Integer), 169(Integer)
```

删除清单标签表的那些数据

```
DELETE FROM task_label WHERE task_id=? 
==> Parameters: 169(Integer)
```

## 获取最近一周的清单完成情况（获取最近七周的清单完成情况）

查询某个时间范围内的清单完成数量和未完成数量

```
select run, count(*)
from task 
where update_time >= '2024-04-13' and update_time < '2024-04-14' 
and user_id = 1
group by run 
```

## 查询用户在当天的每个分类的完成情况

```
select ca.name, count(*)
from category ca inner join task ta on ca.id = ta.category_id
where ta.run = 0 and ta.update_time > CURDATE() and ta.user_id = 1
group by ca.name 
```

## 查询当前用户的所有清单

```
SELECT id,user_id,category_id,name,description,create_time,update_time,run FROM task WHERE (user_id = ? AND run = ?) 
```

## 查询当前用户当前分类所有未完成的清单

```
SELECT id,user_id,category_id,name,description,create_time,update_time,run FROM task WHERE (user_id = ? AND category_id = ? AND run = 1) 
```

## 查询当前用户当前页的所有未完成清单，可根据过滤条件查询

```
SELECT ta.id,ta.user_id,ta.category_id,ta.name,ta.description,ta.create_time,ta.update_time,ta.run 
FROM task ta left join task_label tl on ta.id = tl.task_id
WHERE user_id = ? AND category_id = ? AND run = ?
and tl.label_id in (?, ?)
limit ?, ?
```

```
SELECT ta.id,ta.user_id,ta.category_id,ta.name,ta.description,ta.create_time,ta.update_time,ta.run 
FROM task ta left join task_label tl on ta.id = tl.task_id
WHERE user_id = 1 AND category_id = 1 AND run = 1
and tl.label_id in (1)
limit 0, 5
```

## 获取用户清单的使用情况

获取用户待完成和已完成的清单

0表示已完成

1表示未完成

```
select run, count(*) from task 
where user_id = ?
group by run
```

获取某个时间范围内完成的清单数量

```
select count(*)
from task 
where update_time >= '2024-04-13' and update_time < '2024-04-14' 
and user_id = 0
```























