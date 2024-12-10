-- Enter the name of the band to query out all the member information for that band.
-- Example: Query all members of the band ‘Imagine Dragons’.
SELECT *
FROM Member
WHERE bname = 'Imagine Dragons';

-- Query the writer of the Concert cid=1 and the album it is from
SELECT s.sname, s.swriter, a.aname
FROM Performs p
JOIN Song s ON p.sname = s.sname
JOIN Album a ON s.salbum = a.aname
WHERE p.CID = '1';

-- Query Bangkok city's concert schedule, invited bands, members' names, and division of work.
SELECT c.ctime, b.bname, m.mname, m.mwork
FROM Concert c
JOIN Joins j ON c.CID = j.CID
JOIN Band b ON j.bname = b.bname
JOIN Member m ON b.bname = m.bname
WHERE c.cplace = 'Bangkok';

--Query the names of albums that were popular in concert during the two years 2024-2025. (For each concert performance of a song belonging to an album, the heat value increases by 1.)
SELECT a.aname, COUNT(DISTINCT p.CID) AS total_performance_count
FROM Performs p
JOIN Song s ON p.sname = s.sname
JOIN Album a ON s.salbum = a.aname
WHERE p.CID IN (
    SELECT CID 
    FROM Concert 
    WHERE ctime BETWEEN '2024-01-01' AND '2025-12-31'
)
GROUP BY a.aname
ORDER BY total_performance_count DESC;

-- Query if any bands have a guitarist over the age of 45
SELECT m.bname, m.mname, (strftime('%Y', 'now') - strftime('%Y', m.birthdate)) AS age
FROM Member m
WHERE m.mwork = 'guitarist' 
  AND m.birthdate <= DATE('now', '-45 years');

  --Pattern Matching  Look up members with the letters _zm_ in their names, one Demons song released, on which album, and what the band name is.
SELECT DISTINCT m.mname, b.bname, a.aname
FROM Member m
JOIN Band b ON m.bname = b.bname
JOIN Song s ON s.sname = 'Demons'
JOIN Album a ON s.salbum = a.aname
WHERE m.mname LIKE '%_zm_%';

-- Query the Demons song, out of the london venue, and at which other concerts it was performed.
SELECT DISTINCT c.CID, c.ctime, c.cplace
FROM Performs p
JOIN Concert c ON p.CID = c.CID
WHERE p.sname = 'Demons'
  AND c.cplace <> 'London';


--delete 'new Song'
DELETE FROM Performs 
WHERE sname IN (SELECT sname FROM Song WHERE salbum = 'EA');
DELETE FROM Song 
WHERE sname = 'New Song';
--delete new Album 'EA'
DELETE FROM Performs 
WHERE sname IN (SELECT sname FROM Song WHERE salbum = 'EA');
DELETE FROM Song 
WHERE salbum = 'EA';
DELETE FROM Album 
WHERE aname = 'EA';
-- Add new album
INSERT INTO Album (aname, aband, atime) 
VALUES ('EA', 'Linkin Park', '2024-12-10');
--Add new Song
INSERT INTO Song (sname, salbum, swriter) 
VALUES ('New Song', 'EA', 'Linkin Park');

--Change of place to perform in london on 1 october 2025.
UPDATE Concert
SET ctime = '2025-10-01'
WHERE cplace = 'London' AND ctime <> '2025-10-01';

--Only change ctime
UPDATE Concert
SET ctime = '2025-08-22'
WHERE CID = 3;