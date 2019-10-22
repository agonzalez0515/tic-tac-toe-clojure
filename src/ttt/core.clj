(ns ttt.core
  (:gen-class)
  (:require [ttt.board :as board]
            [ttt.player :as player]
            [ttt.ui :as ui]))


(defn -main
  [& args]
  (ui/start-game-message player/players)
  (board/print-board (board/initial-board))
  (board/make-move (board/initial-board) (player/get-move) "X"))




