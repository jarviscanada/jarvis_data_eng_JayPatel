# SQL Practice

## Table Setup (DDL)

### Table `cd.members`

```sql
CREATE TABLE cd.members (
  memid integer NOT NULL PRIMARY KEY,
  surname varchar(200) NOT NULL,
  firstname varchar(200) NOT NULL,
  address varchar(300) NOT NULL,
  zipcode integer NOT NULL,
  ipcode integer NOT NULL,
  telephone character varying(20) NOT NULL,
  recommendedby integer,
  joindate timestamp NOT NULL,
  CONSTRAINT fk_recommended FOREIGN KEY (recommendedby) REFERENCES cd.members(memid) ON DELETE CASCADE
);
```

### Table `cd.facilities`

```sql
CREATE TABLE cd.facilities (
  facid integer NOT NULL PRIMARY KEY,
  name character varying(100) NOT NULL,
  membercost numeric NOT NULL,
  guestcost numeric NOT NULL,
  initialoutlay numeric NOT NULL,
  monthlymaintenance numeric NOT NULL
);
```

### Table `cd.bookings`

```sql
CREATE TABLE cd.bookings (
  bookid integer NOT NULL PRIMARY KEY,
  facid integer NOT NULL,
  memid integer NOT NULL,
  starttime timestamp NOT NULL,
  slots integer NOT NULL,
  CONSTRAINT fk_bookings_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
  CONSTRAINT fk_bookings_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
);
```

## SQL Queries

## Modifying Data

**1. [Insert](https://pgexercises.com/questions/updates/insert.html)**
```sql
INSERT INTO cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES
    (9, 'Spa', 20, 30, 100000, 800);
```

**2. [Insert with Select](https://pgexercises.com/questions/updates/insert3.html)**
```sql
INSERT INTO cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES
    ((SELECT MAX(facid) FROM cd.facilities) + 1, 'Spa', 20, 30, 100000, 800);
```

**3. [Update](https://pgexercises.com/questions/updates/update.html)**
```sql
UPDATE cd.facilities
SET initialoutlay = 10000
WHERE name = 'Tennis Court 2';
```

**4. [Update with Calculation](https://pgexercises.com/questions/updates/updatecalculated.html)**
```sql
UPDATE cd.facilities
SET membercost = (SELECT membercost * 1.1 FROM cd.facilities WHERE facid = 0),
    guestcost = (SELECT guestcost * 1.1 FROM cd.facilities WHERE facid = 0)
WHERE name='Tennis Court 2';
```

**5. [Delete All](https://pgexercises.com/questions/updates/delete.html)**
```sql
DELETE FROM cd.bookings;
```

**6. [Delete with Condition](https://pgexercises.com/questions/updates/deletewh.html)**
```sql
DELETE FROM cd.members WHERE memid=37;
```

## Basics

**7. [Where Clause](https://pgexercises.com/questions/basic/where2.html)**
```sql
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE monthlymaintenance > 50 * membercost AND membercost != 0;
```

**8. [Where with Like](https://pgexercises.com/questions/basic/where3.html)**
```sql
SELECT * FROM cd.facilities WHERE name LIKE '%Tennis%';
```

**9. [Where with OR](https://pgexercises.com/questions/basic/where4.html)**
```sql
SELECT * FROM cd.facilities WHERE facid = 1 OR facid = 5;
```

**10. [Date Comparison](https://pgexercises.com/questions/basic/date.html)**
```sql
SELECT memid, surname, firstname, joindate
FROM cd.members
WHERE joindate >= '2012-09-01';
```

**11. [Union](https://pgexercises.com/questions/basic/union.html)**
```sql
-- You must know the difference between JOIN and UNION
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;
```

## Join

**12. [Simple Join](https://pgexercises.com/questions/joins/simplejoin.html)**
```sql
SELECT b.starttime
FROM cd.bookings b
INNER JOIN cd.members m ON b.memid = m.memid
WHERE m.firstname ='David' AND m.surname = 'Farrell';
```

**13. [Join with Conditions](https://pgexercises.com/questions/joins/simplejoin2.html)**
```sql
SELECT bk.starttime, fac.name
FROM cd.bookings bk
INNER JOIN cd.facilities fac ON (fac.facid = bk.facid)
WHERE fac.name LIKE 'Tennis%' AND bk.starttime >= '2012-09-21' AND bk.starttime < '2012-09-22'
ORDER BY bk.starttime;
```

**14. [Three Joins](https://pgexercises.com/questions/joins/self2.html)**
```sql
SELECT mem.firstname, mem.surname, ref.firstname, ref.surname
FROM cd.members mem
LEFT OUTER JOIN cd.members ref ON ref.memid = mem.recommendedby
ORDER BY (mem.surname, mem.firstname);
```

**15. [Three Joins with Distinct](https://pgexercises.com/questions/joins/self.html)**
```sql
SELECT DISTINCT ref.firstname, ref.surname
FROM cd.members mem
INNER JOIN cd.members ref ON ref.memid = mem.recommendedby
ORDER BY ref.surname, ref.firstname;
```

**16. [Subquery and Join](https://pgexercises.com/questions/joins/sub.html)**
```sql
SELECT DISTINCT mem.firstname || ' ' || mem.surname AS member,
    (SELECT ref.firstname || ' ' || ref.surname
     FROM cd.members ref
     WHERE ref.memid = mem.recommendedby) AS recommender
FROM cd.members AS mem
ORDER BY member;
```

## Aggregation

**17. [Count with Group By](https://pgexercises.com/questions/aggregates/count3.html)**
```sql
SELECT recommendedby, COUNT(*)
FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;
```

**18. [Sum with Group By](https://pgexercises.com/questions/aggregates/fachours.html)**
```sql
SELECT facid, SUM(slots)
FROM cd.bookings
GROUP BY facid
ORDER BY facid;
```

**19. [Sum with Group By and Condition](https://pgexercises.com/questions/aggregates/fachoursbymonth.html)**
```sql
SELECT facid, SUM(slots)
FROM cd.bookings
WHERE starttime >= '2012-09-01' AND starttime <= '2012-10-1'
GROUP BY facid
ORDER BY SUM(slots);
```

**20. [Sum with Group By and Extract](https://pgexercises.com/questions/aggregates/fachoursbymonth2.html)**
```sql
SELECT facid, EXTRACT(month FROM starttime) AS months, SUM(slots)
FROM cd.bookings
WHERE EXTRACT(year FROM starttime) = '2012'
GROUP BY facid, months;
```

**21. [Count Distinct](https://pgexercises.com/questions/aggregates/members1.html)**
```sql
SELECT COUNT(DISTINCT memid)
FROM cd.bookings;
```

**22. [Group By with Join and Min](https://pgexercises.com/questions/aggregates/nbooking.html)**
```sql
SELECT mb.surname, mb.firstname, mb.memid, MIN(starttime)
FROM cd.members mb
INNER JOIN cd.bookings bk ON bk.memid = mb.memid
WHERE bk.starttime >= '2012-09-01'
GROUP BY mb.memid
ORDER BY mb.memid;
```

**23. [Window Function](https://pgexercises.com/questions/aggregates/countmembers.html)**
```sql
select count(*) over(), firstname, surname
from cd.members
order by joindate   
```

**24. [Window Function with Row Number](https://pgexercises.com/questions/aggregates/nummembers.html)**
```sql
SELECT ROW_NUMBER() OVER(ORDER BY joindate), firstname, surname
FROM cd.members
ORDER BY joindate;
```

**25. [Window Function, Subquery, Group By](https://pgexercises.com/questions/aggregates/fachours4.html)**
```sql
SELECT facid, SUM
FROM (
    SELECT facid,
        ROW_NUMBER() OVER(ORDER BY SUM(slots) DESC) rank,
        SUM(slots) sum
    FROM cd.bookings
    GROUP BY facid
) AS t
WHERE rank = 1;
```
## String

**26. [String Concatenation](https://pgexercises.com/questions/string/concat.html)**
```sql
SELECT surname || ',' || firstname AS name
FROM cd.members;
```

**27. [String Function in WHERE Clause](https://pgexercises.com/questions/string/reg.html)**
```sql
SELECT memid, telephone
FROM cd.members
WHERE telephone LIKE '%(%';
```

**28. [String Function and Group By](https://pgexercises.com/questions/string/substr.html)**
```sql
SELECT SUBSTR(surname, 1, 1) AS first_letter, COUNT(*)
FROM cd.members
GROUP BY first_letter
ORDER BY first_letter;
```

