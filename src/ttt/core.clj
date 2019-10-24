(ns ttt.core
  (:gen-class)
  (:require [ttt.board :as board]
            [ttt.player :as player]
            [ttt.ui :as ui]))

(def game-state (atom {:board (board/initial-board)
                       :players player/players
                       :current-player (first player/players)}))

(defn set-game-state
  [board players]
  (swap! game-state 
        (fn [current-state]
           (merge current-state {:board board :players players :current-player (first players)}))))

(defn play 
  []
  (ui/print-board (:board @game-state))
  (set-game-state 
   (board/make-move (:board @game-state) (player/get-move) (:marker (:current-player @game-state))) 
   (player/switch-players (:players @game-state)))
  (ui/print-board (:board @game-state)))



(defn -main
  [& args]
  (ui/print-start-game-message player/players)
  (play))


;if game-over?
  ;return win message
  ;keep recursing