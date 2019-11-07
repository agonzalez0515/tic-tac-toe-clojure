(ns ttt.game-setup
  (:require [ttt.ui :as ui]
            [database :as db]))


(defn get-usernames
  [players]
  (map ui/get-player-username players))
  
(defn- save-username
  [player]
  (db/save-username player))

(defn- create-usernames-hash
  [names]
  (mapv (partial zipmap [:username :username]) (into [] (map vec (partition 1 names)))))

(defn setup-players 
  [players]
  (let [usernames (get-usernames players)]
    (doseq [player usernames] (save-username player))
    (map merge (create-usernames-hash usernames) players)))
