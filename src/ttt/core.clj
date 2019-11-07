(ns ttt.core
  (:gen-class)
  (:require [ttt.board :as board]
            [ttt.player :as player]
            [ttt.ui :as ui]
            [ttt.game-rules :as rules]
            [ttt.game-setup :as setup]
            [database :as db]))

(def initial-state {:board (board/create-initial-board)
                    :players  (setup/setup-players player/players)})

(defn- set-game-state
  [state board players]
  (assoc state :board board :players players :current-player (first players)))


(defn set-win-stats
  [winner loser]
  (db/update-stats-when-winner winner loser)
  (ui/print-win-message winner)
  (ui/print-stats (db/retrieve-all-player-stats [winner loser])))

(defn set-ties
  [player1 player2]
  (db/update-stats-when-tie player1 player2)
  (ui/print-tie-message)
  (ui/print-stats (db/retrieve-all-player-stats [player1 player2])))

(defn- player2
  [players]
  (:username (second players)))

(defn- player1
  [players]
  (:username (first players)))

(defn play
  [initial-state]
  (loop [state (assoc initial-state :current-player (first (:players initial-state)))]
    (ui/print-board (:board state))
    (ui/print-player-instructions (:username (:current-player state)))
    (cond 
      (rules/win? (:board state)) (set-win-stats (player2 (:players state)) (player1 (:players state)))
      (rules/tie? (:board state)) (set-ties (player1 (:players state)) (player2 (:players state)))
      :else (let [next-board (board/make-move
                        (:board state)
                        (player/get-move)
                        (:marker (:current-player state)))
            next-player (player/switch-players (:players state))]
        (recur (set-game-state state next-board next-player))))))


(defn -main
  []
  (ui/print-start-game-message)
  (play initial-state))
