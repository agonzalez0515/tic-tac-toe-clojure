(ns database-spec
  (:require [speclj.core :refer [context describe it should= stub with-stubs should-invoke]]
            [clojure.java.jdbc :as j]
            [database :as db]))

(def player1-stats {:stats_id 9 :username "Angie" :wins 0 :ties 0 :losses 0})
(def player2-stats {:stats_id 2 :username "Jon" :wins 0 :ties 0 :losses 0})

(describe "Database"
  (with-stubs)  
          
  (context "#save-username"
    (it "saves a username to the db if it does not exist"
      (with-redefs [j/insert! (stub :j/insert {:return {:stats_id 6 :username "Tswift" :wins 0 :ties 0 :losses 0}})
                      j/find-by-keys (stub :j/find-by-keys {:return ()})]
        (should= 6 (:stats_id (db/save-username "Tswift")))))

    (it "updates a username to the db if it exists"
      (with-redefs [j/update! (stub :j/update! {:return {:stats_id 9 :username "Kelly" :wins 0 :ties 0 :losses 0}})
                      j/find-by-keys (stub :j/find-by-keys {:return {:stats_id 9 :username "Kelly" :wins 0 :ties 0 :losses 0}})]
        (should= 9 (:stats_id (db/save-username "Kelly"))))))
      
  (context "#update-stats-when-winner"
    (it "updates usernames stats when there is a winner"
      (with-redefs [db/retrieve-username (stub :db/retrieve-username {:return player1-stats})
                    db/retrieve-username (stub :db/retrieve-username {:return player2-stats})
                    j/update! (stub :j/update! {:return 1})]
        (should-invoke db/update-win {:with ["Angie"]}  (db/update-stats-when-winner "Angie" "Jon"))
        (should-invoke db/update-lost {:with ["Jon"]}  (db/update-stats-when-winner "Angie" "Jon")))))
  
  (context "#update-stats-when-tie"
    (it "updates usernames stats when there is a tie"
      (with-redefs [db/retrieve-username (stub :db/retrieve-username {:return player1-stats})
                    db/retrieve-username (stub :db/retrieve-username {:return player2-stats})
                    j/update! (stub :j/update! {:return 1})]
      (should-invoke db/update-tie {:with ["Angie" "Jon"]} (db/update-stats-when-tie "Angie" "Jon")))))
    
  (context "#retrieve-all-player-stats"
    (it "retrieves all the stats for the players in the game"
      (with-redefs [db/retrieve-username (let [results (atom [player1-stats player2-stats])]
                                     (fn [_] (ffirst (swap-vals! results rest))))]
        (should= [player1-stats player2-stats] (db/retrieve-all-player-stats ["Angie" "Jon"]))))))
    
