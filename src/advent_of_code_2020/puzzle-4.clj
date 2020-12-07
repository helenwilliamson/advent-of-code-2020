(ns advent-of-code-2020.puzzle-4
  (:gen-class)
  (:require [clojure.string :as str]
            [clojure.set]))

(def fields
  #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid" "cid"})

(defn read-input
  ([] (read-input "puzzle-4"))
  ([name]
   (->> (-> (slurp (str "resources/" name))
           (str/split #"\n\n"))
        (mapv #(str/split % #"\s|:"))
        (mapv #(mapv vec (partition 2 %)))
        (map #(into {} %)))))

(defn valid?
  [passport]
  (= 7 (->> (keys passport)
            (into #{})
            (clojure.set/intersection fields)
            (filter (partial not= "cid"))
            count)))

(defn count-valid
  [passports]
  (count (filter valid? passports)))
