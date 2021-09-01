(ns advent-of-code-2020.puzzle-5
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  []
  (->> (slurp "resources/puzzle-5")
       (str/split-lines)))

(defn looper
  [relevant-parts upper-limit decider]
  (loop [desigation relevant-parts
         rows (range 0 upper-limit)]
    (let [current (first desigation)
          partitioned (split-at (/ (count rows) 2) rows)]
      (if (nil? current)
        (first rows)
        (recur (rest desigation) (partitioned (decider current)))))))

(defn row-number
  [boarding-pass]
  (looper (take 7 boarding-pass) 128 #(case %1
                                        \F 0
                                        \B 1)))

(defn column-number
  [boarding-pass]
  (looper (drop 7 boarding-pass) 8 #(case %1
                                      \L 0
                                      \R 1)))

(defn version-1
  []
  (->> (read-input)
       (map #(+ (* (row-number %1) 8) (column-number %1)))
       (sort >)
       first))

(defn index-seats
  [seat-numbers]
  (map vector
       (range (first seat-numbers) (+ (first seat-numbers) (count seat-numbers)))
       seat-numbers))

(defn version-2
  []
  (->> (read-input)
       (map #(+ (* (row-number %1) 8) (column-number %1)))
       (sort <)
       (index-seats)
       (filter #(not= (first %1) (second %1)))
       ffirst))
