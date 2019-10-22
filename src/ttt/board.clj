(ns ttt.board
  (:require [clojure.math.numeric-tower :as math]))

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
  (boolean (some #(= position %) (initial-board))))

(defn valid-move?
  [board position]
  (and (valid? position) (position_available? board position)))

(defn make-move
  [board position marker]
  (if (valid-move? board position)
    (place-marker board position marker)
    (println "Invalid move")))