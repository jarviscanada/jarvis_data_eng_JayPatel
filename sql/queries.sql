-- Modifying Data: Question #1
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) 
VALUES 
  (9, 'Spa', 20, 30, 100000, 800)

-- Modifying Data: Question #2
INSERT INTO cd.facilities (
  facid, name, membercost, guestcost, 
  initialoutlay, monthlymaintenance
) (
  SELECT 
    (
      SELECT 
        MAX(facid) 
      FROM 
        cd.facilities
    ) + 1, 
    'Spa', 
    20, 
    30, 
    100000, 
    800
);

-- Modifying Data: Question #3
UPDATE 
  cd.facilities 
SET 
  initialoutlay = 10000 
WHERE 
  facid = 1;

-- Modifying Data: Question #4
UPDATE 
  cd.facilities facs 
SET 
  membercost = (
    SELECT 
      membercost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ), 
  guestcost = (
    SELECT 
      guestcost * 1.1 
    FROM 
      cd.facilities 
    WHERE 
      facid = 0
  ) 
WHERE 
  facs.facid = 1;

-- Modifying Data: Question #5
DELETE FROM 
  cd.bookings;

-- Modifying Data: Question #6
DELETE FROM 
  cd.members 
WHERE 
  memid = 37;

-- Basics: Question #1
SELECT 
  facid, 
  name, 
  membercost, 
  monthlymaintenance 
FROM 
  cd.facilities 
WHERE 
  membercost > 0 
  AND membercost < monthlymaintenance * 0.02;

-- Basics: Question #2
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  name LIKE '%Tennis%';

-- Basics: Question #3
SELECT 
  * 
FROM 
  cd.facilities 
WHERE 
  facid IN (1, 5);

-- Basics: Question #4
SELECT 
  memid, 
  surname, 
  firstname, 
  joindate 
FROM 
  cd.members 
WHERE 
  joindate >= '2012-09-01';

-- Basics: Question #5
SELECT 
  surname 
FROM 
  cd.members 
UNION 
SELECT 
  name 
FROM 
  cd.facilities;

-- Joins: Question #1
SELECT 
  bks.starttime 
FROM 
  cd.bookings bks 
  JOIN cd.members mems ON mems.memid = bks.memid 
WHERE 
  mems.firstname = 'David' 
  AND mems.surname = 'Farrell';
```

-- Joins: Question #2
SELECT 
  starttime, 
  name 
FROM 
  cd.bookings bks 
  JOIN cd.facilities facs ON bks.facid = facs.facid 
WHERE 
  facs.name IN (
    'Tennis Court 1', 'Tennis Court 2'
  ) 
  AND bks.starttime >= '2012-09-21' 
  AND bks.starttime < '2012-09-22' 
ORDER BY 
  bks.starttime;

-- Joins: Question #3
SELECT 
  mems.firstname AS memfname, 
  mems.surname AS memsname, 
  recs.firstname AS recfname, 
  recs.surname AS recsname 
FROM 
  cd.members mems 
  LEFT OUTER JOIN cd.members recs ON recs.memid = mems.recommendedby 
ORDER BY 
  memsname, 
  memfname;

-- Joins: Question #4
SELECT 
  DISTINCT recs.firstname AS firstname, 
  recs.surname AS surname 
FROM 
  cd.members mems 
  JOIN cd.members recs ON recs.memid = mems.recommendedby 
ORDER BY 
  surname, 
  firstname;

-- Joins: Question #5
SELECT 
  DISTINCT mems.firstname || ' ' || mems.surname AS member, 
  (
    SELECT 
      recs.firstname || ' ' || recs.surname AS recommender 
    FROM 
      cd.members recs 
    WHERE 
      recs.memid = mems.recommendedby
  ) 
FROM 
  cd.members mems 
ORDER BY 
  member;

-- Aggregation: Question #1
SELECT 
  recommendedby, 
  COUNT(*) 
FROM 
  cd.members 
WHERE 
  recommendedby IS NOT NULL 
GROUP BY 
  recommendedby 
ORDER BY 
  recommendedby;

-- Aggregation: Question #2
SELECT 
  facid, 
  sum(slots) 
FROM 
  cd.bookings 
GROUP BY 
  facid 
ORDER BY 
  facid;

-- Aggregation: Question #3
SELECT 
  facid, 
  SUM(slots)
FROM 
  cd.bookings 
WHERE 
  starttime >= '2012-09-01' 
  AND starttime < '2012-10-01' 
GROUP BY 
  facid 
ORDER BY 
  SUM(slots);

-- Aggregation: Question #4
SELECT 
  facid, 
  EXTRACT(
    MONTH 
    FROM 
      starttime
  ) AS month, 
  SUM(slots)
FROM 
  cd.bookings 
WHERE 
  EXTRACT(
    YEAR 
    FROM 
      starttime
  ) = 2012 
GROUP BY 
  facid, 
  month 
ORDER BY 
  facid, 
  month;
```

-- Aggregation: Question #5
SELECT 
  COUNT(DISTINCT memid) 
from 
  cd.bookings;

-- Aggregation: Question #6
SELECT 
  mems.surname, 
  mems.firstname, 
  mems.memid, 
  MIN(bks.starttime) AS starttime 
FROM 
  cd.bookings bks 
  INNER JOIN cd.members mems ON mems.memid = bks.memid 
WHERE 
  starttime >= '2012-09-01' 
GROUP BY 
  mems.surname, 
  mems.firstname, 
  mems.memid 
ORDER BY 
  mems.memid;

-- Aggregation: Question #7
SELECT 
  COUNT(*) OVER(), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;

-- Aggregation: Question #8
SELECT 
  ROW_NUMBER() OVER (
    ORDER BY 
      joindate
  ), 
  firstname, 
  surname 
FROM 
  cd.members 
ORDER BY 
  joindate;

-- Aggregation: Question #9
SELECT 
  facid, 
  total 
FROM 
  (
    SELECT 
      facid, 
      SUM(slots) AS total, 
      RANK() OVER (
        ORDER BY 
          SUM(slots) DESC
      ) AS rank 
    FROM 
      cd.bookings 
    GROUP BY 
      facid
  ) AS ranked 
WHERE 
  rank = 1;

-- String Functions: Question #1
SELECT 
  surname || ', ' || firstname AS name 
FROM 
  cd.members;

-- String Functions: Question #2
SELECT 
  memid, 
  telephone 
FROM 
  cd.members 
WHERE 
  telephone ~ '[()]';

-- String Functions: Question #3
SELECT 
  SUBSTR(mems.surname, 1, 1) AS letter, 
  COUNT(*) AS count 
FROM 
  cd.members mems 
GROUP BY 
  letter 
ORDER BY 
  letter;

