(ns ttt.player)

(defn player 
  [marker]
  {:marker marker})

(def players [(player "X") (player "O")])

(defn current-player
  [[player1 player2]]
  player1)

(defn get-move
  []
  (read-string (read-line)))

(defn switch-players
  [[player-1 player-2]]
  (if (= (:marker player-1) "X")
    [player-2 player-1]
    [player-1 player-2]))