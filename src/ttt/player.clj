(ns ttt.player)

(defn player 
  [marker]
  {:marker marker})

(def players [(player "X") (player "O")])

(defn current-player
  [[player1 player2]]
  player1)

(defn get-player-move
  []
  (read-string (read-line)))
