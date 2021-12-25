(ns contacts.db
  (:require [hugsql.core :as hugsql]))

(def config
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/clj_contacts"
   :user "postgres"
   :password "theotokos21"})

(hugsql/def-db-fns "bookmarks.sql")
