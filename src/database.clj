(ns database
  (:require [clojure.java.jdbc :as j]))

(def db {:dbtype "postgresql"
         :dbname "ttt"
         :host "localhost"
         :user "angie"
         :password "MtNmWUsC"})

(def stats-sql (j/create-table-ddl :stats [[:stats_id :serial "PRIMARY KEY"]
                                           [:username :text "UNIQUE"]
                                           [:wins :int "DEFAULT 0"]
                                           [:ties :int "DEFAULT 0"]
                                           [:losses :int "DEFAULT 0"]]
                                   {:conditional? true}))


(j/execute! db [stats-sql])

(defn- retrieve-username
  [username]
 (into {} (j/find-by-keys db :stats {:username username})))

(defn- username-exists?
  [username]
  (not (= 0 (count (retrieve-username username)))))


(defn- update-win
  [username]
  (let [wins (:wins  (retrieve-username username))]
    (j/update! db :stats {:wins (+ wins 1)} ["username = ?" username])))

(defn- update-lost
  [username]
  (let [losses (:losses (retrieve-username username))]
    (j/update! db :stats {:losses (+ losses 1)} ["username = ?" username])))

(defn- update-tie
  [username1 username2]
  (let [ties-username1 (:ties (retrieve-username username1))
        ties-username2 (:ties (retrieve-username username2))]
    (j/update! db :stats {:ties (+ ties-username1 1)} ["username = ?" username1])
    (j/update! db :stats {:ties (+ ties-username2 1)} ["username = ?" username2])))

(defn save-username
  [username]
  (if (username-exists? username)
    (j/update! db :stats {:username username} ["username = ?" username])
    (j/insert! db :stats {:username username})))

(defn update-stats-when-winner
  [winner loser]
  (update-win winner)
  (update-lost loser))

(defn update-stats-when-tie
  [player1 player2]
  (update-tie player1 player2))

(defn retrieve-all-player-stats
  [names]
  (map retrieve-username names))