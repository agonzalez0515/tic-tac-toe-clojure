(ns ttt.player)

(defn- player 
  [marker]
  {:marker marker})

(def players [(player "X") (player "O")])

(defn get-move
  []
  (read-string (read-line)))

(defn switch-players
  [[current next]]
    [next current])
