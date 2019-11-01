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
                                           [:wins :int "DEFAULT 0"]
                                           [:ties :int "DEFAULT 0"]
                                           [:losses :int "DEFAULT 0"]]
                                   {:conditional? true}))

(println stats-sql)



(j/execute! db [stats-sql])


;need a function to save the username at the start of the game
; check if username exists, if not, insert

;need a function to save each username's result at the end of the game

(defn save-username
  [username]
  (j/insert! db :stats {:username username}))