-- 1.
INSERT INTO cd.facilities 
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) 
VALUES
    (9, 'Spa', 20, 30, 100000, 800);

-- 2.
INSERT INTO cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
VALUES
    ((SELECT MAX(facid) FROM cd.facilities) + 1, 'Spa', 20, 30, 100000, 800);


-- 3.
UPDATE cd.facilities 
SET initialoutlay = 10000 
WHERE name = 'Tennis Court 2';


-- 4.
UPDATE cd.facilities 
SET membercost = (SELECT membercost * 1.1 FROM cd.facilities WHERE facid = 0),
    guestcost = (SELECT guestcost * 1.1 FROM cd.facilities WHERE facid = 0) 
WHERE name = 'Tennis Court 2';


-- 5.
DELETE FROM cd.bookings;


-- 6.
DELETE FROM cd.members WHERE memid = 37;


-- 7.
SELECT facid, name, membercost, monthlymaintenance 
FROM cd.facilities 
WHERE monthlymaintenance > 50 * membercost AND membercost != 0;


-- 8.
SELECT * FROM cd.facilities WHERE name LIKE '%Tennis%';

-- 9.
SELECT * FROM cd.facilities WHERE facid = 1 OR facid = 5;


-- 10.
SELECT memid, surname, firstname, joindate 
FROM cd.members 
WHERE joindate >= '2012-09-01';


-- 11.
SELECT surname FROM cd.members 
UNION 
SELECT name FROM cd.facilities;


-- 12.
SELECT b.starttime 
FROM cd.bookings b 
INNER JOIN cd.members m ON b.memid = m.memid 
WHERE m.firstname ='David' AND m.surname = 'Farrell';


-- 13.
SELECT bk.starttime, fac.name 
FROM cd.bookings bk 
INNER JOIN cd.facilities fac ON (fac.facid = bk.facid)  
WHERE fac.name LIKE 'Tennis%' AND bk.starttime >= '2012-09-21' AND bk.starttime < '2012-09-22' 
ORDER BY bk.starttime;


-- 14.
SELECT mem.firstname, mem.surname, ref.firstname, ref.surname 
FROM cd.members mem 
LEFT OUTER JOIN cd.members ref ON ref.memid = mem.recommendedby 
ORDER BY (mem.surname, mem.firstname);


-- 15.
SELECT DISTINCT ref.firstname, ref.surname
FROM cd.members mem 
INNER JOIN cd.members ref ON ref.memid = mem.recommendedby 
ORDER BY ref.surname, ref.firstname;

-- 16.
SELECT DISTINCT mem.firstname || ' ' || mem.surname AS member, 
    (SELECT ref.firstname || ' ' || ref.surname 
     FROM cd.members ref 
     WHERE ref.memid = mem.recommendedby) AS recommender 
FROM cd.members AS mem 
ORDER BY member;

-- 17.
SELECT recommendedby, COUNT(*) 
FROM cd.members 
WHERE recommendedby IS NOT NULL  
GROUP BY recommendedby 
ORDER BY recommendedby;

-- 18.
SELECT facid, SUM(slots) 
FROM cd.bookings 
GROUP BY facid 
ORDER BY facid;

-- 19.
SELECT facid, SUM(slots) 
FROM cd.bookings 
WHERE starttime >= '2012-09-01' AND starttime <= '2012-10-1'  
GROUP BY facid 
ORDER BY SUM(slots);

-- 20.
SELECT facid, EXTRACT(month FROM starttime) AS months, SUM(slots) 
FROM cd.bookings 
WHERE EXTRACT(year FROM starttime) = '2012' 
GROUP BY facid, months;

-- 21.
SELECT COUNT(DISTINCT memid) 
FROM cd.bookings;

-- 22.
SELECT mb.surname, mb.firstname, mb.memid, MIN(starttime) 
FROM cd.members mb 
INNER JOIN cd.bookings bk ON bk.memid = mb.memid 
WHERE bk.starttime >= '2012-09-01' 
GROUP BY mb.memid 
ORDER BY mb.memid;


-- 23.
SELECT COUNT(*) over(), firstname, surname  
FROM cd.members 
ORDER BY joindate;

-- 24.
SELECT ROW_NUMBER() OVER(ORDER BY joindate), firstname, surname 
FROM cd.members 
ORDER BY joindate;

-- 25.
SELECT facid, sum 
FROM (
    SELECT facid, 
        ROW_NUMBER() OVER(ORDER BY SUM(slots) DESC)  rank, 
        SUM(slots) sum 
    FROM cd.bookings 
    GROUP BY facid
) AS t 
WHERE rank = 1;

-- 26.
SELECT surname || ',' || firstname AS name 
FROM cd.members;

-- 27.
SELECT memid, telephone 
FROM cd.members 
WHERE telephone LIKE '%(%';

-- 28.
SELECT SUBSTR(surname, 1, 1) AS first_letter, COUNT(*) 
FROM cd.members 
GROUP BY first_letter 
ORDER BY first_letter;
