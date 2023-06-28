(ns isomorphic-clojure-webapp.main
  (:gen-class)
  (:require [duct.core :as duct]
            [isomorphic-clojure-webapp.loader :refer [read-config]]))


(duct/load-hierarchy)


(defn -main
  [& args]
  (let [keys     (or (duct/parse-keys args) [:duct/daemon])
        profiles [:duct.profile/prod]]
    (duct/exec-config (read-config) profiles keys)
    (System/exit 0)))
