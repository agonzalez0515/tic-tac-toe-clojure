(ns ttt.game-rules
  (:require [clojure.math.numeric-tower :as math]))

(defn- board-size
  [board]
  (math/sqrt (count board)))

(defn- create-columns
  [board]
 (apply mapv vector (partition (board-size board) board)))

(defn- create-rows
  [board]
  (apply mapv vector (create-columns board)))
  
(defn- create-diagonals
  [board]
  (let [rows (create-rows board)]
    ((rows 0) 0) ((rows 1) 1) ((rows) 2) 2))



; (defn win?
;   [board])

; (((rows 0) 0) ((rows 1) 1) ((rows) 2) 2)


; into [] (map #())