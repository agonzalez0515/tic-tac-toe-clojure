(ns ttt.ui-spec
  (:require [speclj.core :refer [context describe it should=]]
            [ttt.ui :as ui]))

(def player {:marker "X" :username "Angie"})
(def stats [{:stats_id 9 :username "Angie" :wins 2 :ties 0 :losses 0} {:stats_id 2 :username "Jon" :wins 0 :ties 0 :losses 2}])

(describe "UI"
  (context "it prints messages"
    (it "prints a message when the game starts"
      (should= "Let's play tic-tac-toe.\n" 
        (with-out-str (ui/print-start-game-message))))
    (it "prints a board"
      (should= "\n0 | 1 | 2\n---------\n3 | 4 | 5\n---------\n6 | 7 | 8\n" 
        (with-out-str (ui/print-board [0 1 2 3 4 5 6 7 8]))))
    (it "prints an invalid move message"
      (should= "Invalid move. Please try again.\n" (with-out-str (ui/print-invalid-move-message))))
    (it "prints the user's name"
        (should= "Angie, please make a move.\n" (with-out-str (ui/print-make-move-with-name "Angie"))))
    (it "prints the winner when the game is over if there is a winner"
      (should= "Angie wins the game\n" (with-out-str (ui/print-win-message (:username player)))))
    (it "prints instructions for current player"
      (should= "Angie enter a number between 0-8 to select a spot.\n" (with-out-str (ui/print-player-instructions (:username player))))))

  (context "it gets player's input"        
    (it "gets player's username"
      (should= "Angie" (with-in-str "Angie" (ui/get-player-username {:marker "X"})))))
          
  (context "print-stats"
    (it "prints the players stats"
     (should= "====PLAYER STATS====\nAngie- wins: 2 losses: 0 ties: 0\nJon- wins: 0 losses: 2 ties: 0\n" (with-out-str (ui/print-stats stats))))))
