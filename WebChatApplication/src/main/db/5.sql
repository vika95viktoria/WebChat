/*
-- Query: SELECT 
 user_id, COUNT(*)
FROM 
 messages
 group by
 user_id
Having
COUNT(*)  > 2
LIMIT 0, 1000

-- Date: 2015-05-17 18:55
*/
INSERT INTO `TABLE` (`user_id`,`COUNT(*)`) VALUES (3,3);
INSERT INTO `TABLE` (`user_id`,`COUNT(*)`) VALUES (5,3);
