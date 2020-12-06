(ns advent-of-code-2020.puzzle-2
  (:gen-class)
  (:require [clojure.string :as str]))

(defn tokenise
  [line]
  (let [parts (str/split line #": ")
        password (parts 1)
        rules (str/split (parts 0) #" ")
        letter (rules 1)
        policy (str/split (rules 0) #"-")
        min-letter (Integer/parseInt (policy 0))
        max-letter (Integer/parseInt (policy 1))]
    {:password password :letter letter :min-letter min-letter :max-letter max-letter}))

(defn read-input
  []
  (->> (slurp "resources/puzzle-2")
       (str/split-lines)
       (map tokenise)))

(defn validate-password
  [data]
  (let [number-of-matches (-> (:letter data)
                              (re-pattern)
                              (re-seq (:password data))
                              (count))]
    (and (>= number-of-matches (:min-letter data))
         (<= number-of-matches (:max-letter data)))))

(defn checker
  []
  (->> (read-input)
       (filter validate-password)
       (count)))
