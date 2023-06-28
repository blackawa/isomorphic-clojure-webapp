(ns isomorphic-clojure-webapp.loader
  (:require [clojure.java.io :as io]
            [duct.core :as duct]))


(def ^:private config-files
  ["config.edn"
   "config.http_schema.edn"])


(defn- migration
  [name]
  (let [->ddl-string #(-> (format "migrations/%s.%s.sql" name %)
                          io/resource slurp)]
    {:up [(->ddl-string "up")]
     :down [(->ddl-string "down")]}))


(def ^:private readers
  {'migration migration})


(defn read-config
  []
  (->> config-files
       (map duct/resource)
       (map #(duct/read-config % readers))
       (reduce #(duct/merge-configs %1 %2) {})))
