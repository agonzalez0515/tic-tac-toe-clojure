(ns ttt.ui
    (:require [clojure.math.numeric-tower :as math]))

(defn- add-padding-to-cells
  [row]
  (clojure.string/join " | " row))

(defn- print-first-player-message
  [[player1 player2]]
  (println "Player 1 is" (:marker player1))
  (println "Player 2 is" (:marker player2)))

(defn print-player-intructions
  []
  (println "Enter a number between 0-8 to select a spot."))

(defn print-start-game-message
  [players]
  (println "Let's play tic-tac-toe.")
  (print-first-player-message players)
  (print-player-intructions))

(defn print-board
  [board]
  (let [size (math/sqrt (count board))
        rows (into [] (map add-padding-to-cells (partition size board)))
        divider (str "\n" (clojure.string/join (repeat (count (first rows)) "-")) "\n")]
      (println (str "\n" (clojure.string/join divider rows)))))

(defn print-invalid-move-message
  []
  (println "Invalid move. Please try again."))