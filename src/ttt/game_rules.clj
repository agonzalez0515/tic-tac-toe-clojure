(ns ttt.game-rules
  (:require [clojure.math.numeric-tower :as math]))

(defn- get-board-size
  [board]
  (math/sqrt (count board)))

(defn- create-columns
  [board]
 (apply mapv vector (partition (get-board-size board) board)))

(defn- create-rows
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
  (let [rows (create-rows board)]
    (create-diagonal rows)))

(defn- create-right-to-left-diagonal
  [board]
  (let [rows (reverse (create-rows board))]
    (create-diagonal rows)))

(defn- create-groups-of-cells
  [board]
  [(create-rows board) (create-columns board) [(create-left-to-right-diagonal board) (create-right-to-left-diagonal board)]])

(defn- check-group-of-cells
  [cells]
  (map (fn[collection] (= 1 (count (set collection)))) cells))

(defn- available-spaces?
  [board]
  (boolean (some true? (map number? board))))

(defn win?
  [board]
  (boolean (some true? (flatten (map check-group-of-cells (create-groups-of-cells board))))))

(defn tie?
  [board]
  (and (not (available-spaces? board)) (not (win? board))))

(defn game-over?
  [board]
  (or (win? board) (tie? board)))
