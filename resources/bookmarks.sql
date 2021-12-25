-- :name create-bookmarks-table
-- :command :execute
-- :result :raw
-- :doc creates bookmarks table
CREATE TABLE bookmarks (
       id SERIAL PRIMARY KEY,
       name TEXT,
       url TEXT,
       description TEXT,
       created_at TIMESTAMP NOT NULL DEFAULT current_timestamp
);

-- :name get-all-bookmarks
SELECT * from bookmarks;

-- :name get-bookmark-by-id :? :*
SELECT * from bookmarks where id=:id;

-- :name insert-bookmark :insert :*
INSERT INTO bookmarks (name, url, description)
VALUES (:name, :url, :description)
RETURNING id;

-- :name update-bookmark-by-id :! :1
UPDATE bookmarks
SET name=:name, url=:url, description=:description
WHERE id=:id;

-- :name delete-bookmark :! :1
DELETE from bookmarks where id=:id;
