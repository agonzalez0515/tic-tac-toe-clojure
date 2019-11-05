(ns ttt.game-setup
  (:require [ttt.ui :as ui]))


(defn get-players
  [players]
  (map ui/get-player-username players))
  
(defn save-username
  [player]
  (db/save-username player))

(defn setup-players 
  [players]
  (doseq [player (get-players players)] (save-username player)))