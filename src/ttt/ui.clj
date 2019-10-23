(ns ttt.ui
    (:require [clojure.math.numeric-tower :as math]))

(defn first-player-message
  [[player1 player2]]
  (println "Player 1 is" (:marker player1)))

(defn start-game-message
  [players]
  (println "Let's play tic-tac-toe.")
  (first-player-message players)
  (println "Enter a number between 0-8 to select a spot."))

(defn add-padding
  [row]
  (clojure.string/join " | " row))

(defn print-board
  [board]
  (let [size (math/sqrt (count board))]
    (let [rows (into [] (map add-padding (partition size board)))
          divider (str "\n" (clojure.string/join (repeat (count (first rows)) "-")) "\n")]
      (println (str "\n" (clojure.string/join divider rows))))))