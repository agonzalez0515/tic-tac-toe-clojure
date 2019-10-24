(ns ttt.player-spec
  (:require [speclj.core :refer :all]
            [ttt.player :refer :all]))

(describe "Player"
  (context "#players"
    (it "creates a player with marker X"
      (should= "X" (:marker (first players))))
    (it "creates a player with marker O"
      (should= "O" (:marker (second players)))))
          
  (context "#get-move"        
    (it "gets player input and converts to integer"
      (should= 1 (with-in-str "1" (get-move)))))
          
  (context "#switch-players"        
    (it "switches the order of players"
      (should= "O" (:marker (first (switch-players [{:marker "X"} {:marker "O"}])))))))
