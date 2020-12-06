(ns advent-of-code-2020.puzzle-2
  (:gen-class)
  (:require [clojure.string :as str]))

(defn tokenise-old-rules
  [line]
  (let [parts (str/split line #": ")
        password (parts 1)
        rules (str/split (parts 0) #" ")
        letter (rules 1)
        policy (str/split (rules 0) #"-")
        min-letter (Integer/parseInt (policy 0))
        max-letter (Integer/parseInt (policy 1))]
    {:password password :letter letter :min-letter min-letter :max-letter max-letter}))

(defn tokenise-new-rules
  [line]
  (let [parts (str/split line #": ")
        password (parts 1)
        rules (str/split (parts 0) #" ")
        letter (rules 1)
        policy (str/split (rules 0) #"-")
        first-pos (Integer/parseInt (policy 0))
        second-pos (Integer/parseInt (policy 1))]
    {:password password :letter letter :first-pos first-pos :second-pos second-pos}))

(defn read-input
  [new-rules]
  (->> (slurp "resources/puzzle-2")
       (str/split-lines)
       (map #(if new-rules (tokenise-new-rules %) (tokenise-old-rules %)))))

(defn old-validate-password
  [data]
  (let [number-of-matches (-> (:letter data)
                              (re-pattern)
                              (re-seq (:password data))
                              (count))]
    (and (>= number-of-matches (:min-letter data))
         (<= number-of-matches (:max-letter data)))))

(defn position-checker
  [[letter position password]]
  (= letter (subs password (- position 1) position)))

(defn new-validate-password
  [data]
  (->> [[(:letter data) (:first-pos data) (:password data)]
        [(:letter data) (:second-pos data) (:password data)]]
      (filter position-checker)
      (count)
      (= 1)))

(defn old-checker
  []
  (->> (read-input false)
       (filter old-validate-password)
       (count)))

(defn new-checker
  []
  (->> (read-input true)
       (filter new-validate-password)
       (count)))
