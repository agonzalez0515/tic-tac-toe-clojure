(ns ttt.game-setup-spec
  (:require [speclj.core :refer [context should-invoke describe it should= stub with-stubs]]
            [ttt.game-setup :as setup]
            [ttt.ui :as ui]
            [database :as db]))

(describe "Game Setup"
  (with-stubs)
  
  (context "#get-usernames"
    (it "creates a list of players"
      (with-redefs [ui/get-player-username (let [results (atom ["Angie" "Jon"])]
                                              (fn [_] (ffirst (swap-vals! results rest))))]
        (should= ["Angie" "Jon"] (setup/get-usernames [{:marker "X"} {:marker "O"}])))))
  
  (context "#setup-players"
    (it "saves a username" 
      (with-redefs [db/save-username (stub :db/save-username {:return []})
                    setup/get-usernames (stub :setup/get-usernames {:return ["Angie"]})]
        (should-invoke db/save-username {:with ["Angie"]} (setup/setup-players [{:marker "X"} {:marker "O"}] ))))
            
    (it "saves a set of usernames"
      (with-redefs [db/save-username (stub :db/save-username {:return []})
                    setup/get-usernames (stub :setup/get-usernames {:return ["Angie" "Jon"]})]
        (should-invoke db/save-username {:with ["Jon"]} (setup/setup-players [{:marker "X"} {:marker "O"}]))))
           
    (it "creates a player with a username"
        (with-redefs [db/save-username (stub :db/save-username {:return []})
                      setup/get-usernames (stub :setup/get-usernames {:return ["Angie" "Jon"]})]
          (should-invoke db/save-username {:with ["Jon"]} (setup/setup-players [{:marker "X"} {:marker "O"}]))))
    
    (it "returns players with usernames"
        (with-redefs [db/save-username (stub :db/save-username {:return []})
                      setup/get-usernames (stub :setup/get-usernames {:return ["Angie" "Jon"]})]
          (should= '({:username "Angie", :marker "X"} {:username "Jon", :marker "O"}) (setup/setup-players [{:marker "X"} {:marker "O"}]))))))