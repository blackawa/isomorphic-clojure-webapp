(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [fipp.edn :refer [pprint]]
            [cljstyle.task.fix :as formatter]
            [clj-kondo.core :as linter]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.core :as duct]
            [duct.core.repl :as duct-repl :refer [auto-reset]]
            [eftest.runner :as eftest]
            [integrant.core :as ig]
            [integrant.repl :refer [clear halt go init prep reset]]
            [integrant.repl.state :refer [config system]]
            [isomorphic-clojure-webapp.loader :refer [read-config]]))

(duct/load-hierarchy)


(defn test []
  (eftest/run-tests (eftest/find-tests "test")))


(defn format:fix
  []
  (formatter/task ["src" "test"])
  nil)


(defn lint:check
  []
  (-> (linter/run! {:lint ["src" "test"]})
      linter/print!))


(def profiles
  [:duct.profile/dev :duct.profile/local])


(clojure.tools.namespace.repl/set-refresh-dirs "dev/clj/src" "src/clj" "test/clj")

(when (io/resource "local.clj")
  (load "local"))

(integrant.repl/set-prep! #(duct/prep-config (read-config) profiles))
