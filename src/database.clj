(ns database
  (:require [clojure.string :as str]
            [clojure.java.jdbc :as j]))

(def db {:dbtype "postgresql"
         :dbname "ttt"
         :host "localhost"
         :user "angie"
         :password "MtNmWUsC"})

(def stats-sql (j/create-table-ddl :stats [[:stats_id :serial "PRIMARY KEY"]
                                           [:username "TEXT"]
                                           [:wins "INTEGER"]
                                           [:ties "INTEGER"]
                                           [:losses "INTEGER"]]))

(println stats-sql)

(j/execute! db [stats-sql])
