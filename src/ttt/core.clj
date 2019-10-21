(ns ttt.core
  (:require [clojure.math.numeric-tower :as math]))

;====BOARD====
(defn initial-board 
  ([board-size]
   (into [] (range (* board-size board-size))))
   ([]
    (initial-board 3)))

(defn add-padding
  [row]
  (clojure.string/join " | " row))

(defn print-board
  [board]
  (let [size (math/sqrt (count board))]
    (let [rows (into [] (map add-padding (partition size board)))
          divider (str "\n" (clojure.string/join (repeat (count (first rows)) "-")) "\n")]
      (println (str "\n" (clojure.string/join divider rows))))))

(defn place-marker
  [board position marker]
  (assoc board position marker))

(defn position_available?
  [board position]
  (= (nth board position) position)) 

(defn valid?
  [position]
  (if (some #(= position %) (initial-board)) 
    true 
    false))

(defn valid-move? 
  [board position]
  (and (valid? position) (position_available? board position)))

(defn make-move
  [board position marker]
  (if (valid-move? board position)
    (place-marker board position marker)
    (println "Invalid move")))

;====Player=====
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


;====UI=====

(defn first-player-message
  [[player1 player2]]
  (println "Player 1 is" (:marker player1)))

(defn start-game-message
  []
  (println "Let's play tic-tac-toe.")
  (first-player-message players)
  (println "Enter a number between 0-8 to select a spot."))

;====Game====
(defn start-game
  []
  (start-game-message)
  )

(start-game)
