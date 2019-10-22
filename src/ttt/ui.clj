(ns ttt.ui)

(defn first-player-message
  [[player1 player2]]
  (println "Player 1 is" (:marker player1)))

(defn start-game-message
  [players]
  (println "Let's play tic-tac-toe.")
  (first-player-message players)
  (println "Enter a number between 0-8 to select a spot."))