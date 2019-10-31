(ns ttt.core
  (:gen-class)
  (:require [ttt.board :as board]
            [ttt.player :as player]
            [ttt.ui :as ui]
            [ttt.game-rules :as rules]))


(def game-state {:board (board/create-initial-board)
                :players player/players
                :current-player (first player/players)})

(defn set-game-state
  [state board players]
  (assoc state :board board :players players :current-player (first players)))

(defn play
  [starting-game-sate]
  (loop [state starting-game-sate]
    (ui/print-board (:board state))
  (if (rules/game-over? (:board state))
    (ui/print-game-over-message)
    (let [next-board (board/make-move (:board state) (player/get-move) (:marker (:current-player state)))
          next-player (player/switch-players (:players state))]
      (recur (set-game-state state next-board next-player ))))))


(defn play2 
  [board]
  (if (rules/game-over? board)
    (ui/print-game-over-message)))



(defn -main
  []
  (ui/print-start-game-message player/players)
  (play game-state))

