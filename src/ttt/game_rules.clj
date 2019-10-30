(ns ttt.game-rules
  (:require [clojure.math.numeric-tower :as math]))

(defn- get-board-size
  [board]
  (math/sqrt (count board)))

(defn- create-columns
  [board]
 (apply mapv vector (partition (get-board-size board) board)))

(defn create-rows
  [board]
  (apply mapv vector (create-columns board)))
  
(defn- get-cell-value
  [row-with-index]
  (first (map (fn [[index row]] (row index)) row-with-index)))

(defn- create-diagonal
  [cells]
    (let [row (map-indexed hash-map cells)]
      (into [] (map get-cell-value row))))

(defn- create-left-to-right-diagonal
  [board]
  (create-diagonal (create-rows board)))

(defn- create-right-to-left-diagonal
  [board]
  (create-diagonal (reverse (create-rows board))))

(defn- create-groups-of-cells
  [board]
  [(create-rows board) (create-columns board) [(create-left-to-right-diagonal board) (create-right-to-left-diagonal board)]])

(defn- check-group-of-cells
  [cells]
  (map (fn[collection] (= 1 (count (set collection)))) cells))

(defn- available-spaces?
  [board]
  (some number? board))

(defn win?
  [board]
  (boolean (some true? (flatten (map check-group-of-cells (create-groups-of-cells board))))))

(defn tie?
  [board]
  (not (or (win? board) (available-spaces? board))))

(defn game-over?
  [board]
  (or (win? board) (tie? board)))
