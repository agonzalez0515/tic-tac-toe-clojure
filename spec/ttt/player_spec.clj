(ns ttt.player-spec
  (:require [speclj.core :refer [describe should= context it]]
            [ttt.player :as player]))

(describe "Player"
  (context "#players"
    (it "creates a set of players"
      (should= [{:marker "X"} {:marker "O"}] player/players)))
          
  (context "#get-move"        
    (it "gets player input and converts to integer"
      (should= 1 (with-in-str "1" (player/get-move)))))
          
  (context "#switch-players"        
    (it "switches the order of players"
      (should= "O" (:marker (first (player/switch-players [{:marker "X"} {:marker "O"}])))))))
  