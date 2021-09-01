(ns advent-of-code-2020.puzzle-6
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.set :as cset]))

(defn read-input
  []
  (map str/split-lines (-> (slurp "resources/puzzle-6")
                           (str/split #"\n\n"))))

(defn someone-answered
  [group-answers]
  (into #{} (flatten (map #(into [] %1) group-answers))))

(defn everyone-answered
  [group-answers]
  (apply cset/intersection (map #(into (hash-set) %1) group-answers)))

(defn version-1
  []
  (->> (read-input)
       (map someone-answered)
       (map count)
       (reduce +)))

(defn version-2
  []
  (->> (read-input)
       (map everyone-answered)
       (map count)
       (reduce +)))
