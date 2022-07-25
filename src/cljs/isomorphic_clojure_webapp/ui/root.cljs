(ns isomorphic-clojure-webapp.ui.root
  (:require
    [rum.core :refer [defc]]))


(defc root
  []
  [:h1 "Hello from ClojureScript!"])
