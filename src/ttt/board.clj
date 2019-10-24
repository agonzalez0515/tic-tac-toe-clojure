(ns ttt.board
  (:require [clojure.math.numeric-tower :as math]
            [ttt.ui :as ui]))

(defn- valid-input?
  [board position]
  (boolean (some #(= position %) board)))

(defn- place-marker
  [board position marker]
  (assoc board position marker))

(defn create-initial-board
  ([board-size]
   (into [] (range (math/expt board-size 2))))
  ([]
   (create-initial-board 3)))

(defn make-move
  [board position marker]
  (if (valid-input? board position)
    (place-marker board position marker)
    (ui/print-invalid-move-message)))