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

(defn mandatory-fields?
  [passport]
  (= 7 (->> (keys passport)
            (into #{})
            (clojure.set/intersection fields)
            (filter (partial not= "cid"))
            count)))

(defn count-mandatory
  [passports]
  (count (filter mandatory-fields? passports)))

(defn hgt-checker
  [value]
  (let [[[text amount units]] (re-seq #"(?<value>\d+)(?<units>cm|in)" value)]
    (when (some? text)
      (case units
        "cm" (<= 150 (Integer/parseInt amount) 193)
        "in" (<= 59 (Integer/parseInt amount) 76)))))

(def eye-colours #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})

(defn valid?
  [passport]
  (let [byr-checker (fn [value] (and (>= value 1920) (<= value 2020)))
        iyr-checker (fn [value] (and (>= value 2010) (<= value 2020)))
        eyr-checker (fn [value] (and (>= value 2020) (<= value 2030)))
        hcl-checker (fn [value] (some? (re-matches #"#[0-9a-f]{6}" value)))
        ecl-checker (fn [value] (contains? eye-colours value))
        pid-checker (fn [value] (some? (re-matches #"[0-9]{9}" value)))]
    (and (mandatory-fields? passport)
         (byr-checker (Integer/parseInt (passport "byr")))
         (iyr-checker (Integer/parseInt (passport "iyr")))
         (eyr-checker (Integer/parseInt (passport "eyr")))
         (hgt-checker (passport "hgt"))
         (hcl-checker (passport "hcl"))
         (ecl-checker (passport "ecl"))
         (pid-checker (passport "pid")))))

(defn count-valid
  [passports]
  (count (filter valid? passports)))
