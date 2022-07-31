(ns isomorphic-clojure-webapp.ui.pages.start
  (:require [rum.core :refer [defc]]))


(defc ui
  []
  [:div
   [:h1 "Hello from ClojureScript!"]])
