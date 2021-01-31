# Производительность индеков

## Запрос для поиска анкет по префиксу имени и фамилии (одновременно)
```sql
EXPLAIN select id, login, name, surname, gender, age, city from SocialNetwork.users WHERE name like '%' and surname like '%' LIMIT 500
```
- План запроса без индекса
```
+--+-----------+-----+----------+----+-------------+----+-------+----+-------+--------+-----------+
|id|select_type|table|partitions|type|possible_keys|key |key_len|ref |rows   |filtered|Extra      |
+--+-----------+-----+----------+----+-------------+----+-------+----+-------+--------+-----------+
|1 |SIMPLE     |users|NULL      |ALL |NULL         |NULL|NULL   |NULL|1113702|1.23    |Using where|
+--+-----------+-----+----------+----+-------------+----+-------+----+-------+--------+-----------+
```

## Нагрузочные тесты с wrk
 [Скрипт для генерации запросов на Lua](wrk/search-test.lua)

```shell script
wrk -t1 -c1 -d120s -s ./wrk/search-test.lua  http://localhost:80
```
- Результаты
```
Load    Requests/sec:	LatencyAvg  
1       1.61	        620.50ms	  
10      8.00	        1.23s	       
100     8.06	        596.62ms	   
1000 	6.56	        1.21s	       
```

## Запрос построения индекса
```sql
CREATE INDEX name_surname_idx ON users(name, surname) USING btree;
```
- Индекс такой, тк поиск по name, surname, может быть только по name и нужна упорядоченность
- План запроса после построения индекса
```
+--+-----------+-----+----------+-----+------------------------+------------------------+-------+----+------+--------+---------------------+
|id|select_type|table|partitions|type |possible_keys           |key                     |key_len|ref |rows  |filtered|Extra                |
+--+-----------+-----+----------+-----+------------------------+------------------------+-------+----+------+--------+---------------------+
|1 |SIMPLE     |users|NULL      |range|     name_surname_idx   |     name_surname_idx   |  206  |NULL| 82288|  11.11 |Using index condition|
+--+-----------+-----+----------+-----+------------------------+------------------------+-------+----+------+--------+---------------------+
```

- детализация 
```json
{
  "query_block": {
    "select_id": 1,
    "cost_info": {
      "query_cost": "38988.61"
    },
    "table": {
      "table_name": "users",
      "access_type": "range",
      "possible_keys": [
        "name_surname_idx"
      ],
      "key": "name_surname_idx",
      "used_key_parts": [
        "name"
      ],
      "key_length": "206",
      "rows_examined_per_scan": 82288,
      "rows_produced_per_join": 9142,
      "filtered": "11.11",
      "index_condition": "((`SocialNetwork`.`users`.`name` like '?%') and (`SocialNetwork`.`users`.`surname` like '?%'))",
      "cost_info": {
        "read_cost": "38074.39",
        "eval_cost": "914.22",
        "prefix_cost": "38988.61",
        "data_read_per_join": "4M"
      },
      "used_columns": [
        "id",
        "login",
        "name",
        "surname",
        "gender",
        "age",
        "city"
      ]
    }
  }
}
```
- Результаты нагрузочных тестов с wrk c индексом на таблице users. Видны улучшения в работе системы
```
Load	            Requests/sec:	Latency Avg
1 connections	    3.77	        264.61ms
10 connections	    20.67	        482.21ms
100 connections	    19.75	        972.78ms
1000 connections    21.06	        987.26ms
```

При каждом запросе /api/account/search выполняется запрос в БД на получение пользовательских прав
```sql
select login, password, role from SocialNetwork.auth where login = :login
```
это создает дополнительную нагрузку на БД 
- Варианты решения проблемы 
- закешировать рещультат на уровне приложения
- создать индекс на таблице auth по полю login
```sql
CREATE INDEX login_idx ON auth(login) USING btree;
```

- Результаты нагрузочных тестов с wrk c индексом на таблице users и auth
```
Load	            Requests/sec:	Latency Avg
1 connections	    126.34	         9.14ms
10 connections	    386.71	        36.32ms
100 connections	    411.95	        248.88ms
1000 connections    407.23	        1.19s
```

По результатам нагрузочних тестов видно, что производительность системы после добавления индексов 
на таблицы user и auth увиличилась в разы. Из графиков БД MYSQL
видно, что пропали scan запросы, остались только range

- [графики приложения wrk-t1-c1-d120s](index-load-test/screenshots/social-network-backend/wrk-t1-c1-d120s.png)
- [графики MYSQL wrk-t1-c1-d120s](index-load-test/screenshots/mysql/mysqlwrk-t1-c1-d120s.png)

- [графики приложения wrk-t1-c10-d120s](index-load-test/screenshots/social-network-backend/wrk-t1-c10-d120s.png)
- [графики MYSQL wrk-t1-c10-d120s](index-load-test/screenshots/mysql/mysqlwrk-t1-c10-d120s.png)

- [графики приложения wrk-t1-c100-d120s](index-load-test/screenshots/social-network-backend/wrk-t1-c100-d120s.png)
- [графики MYSQL wrk-t1-c100-d120s](index-load-test/screenshots/mysql/mysqlwrk-t1-c100-d120s.png)

- [графики приложения wrk-t1000-c1-d120s](index-load-test/screenshots/social-network-backend/wrk-t1-c1000-d120s.png)
- [графики MYSQL wrk-t1-c1000-d120s](index-load-test/screenshots/mysql/mysqlwrk-t1-c1000-d120s.png)

- [общий график MYSQL](index-load-test/screenshots/mysql/mysql.png)

