(ns isomorphic-clojure-webapp.ui.pages.errors
  (:require [rum.core :refer [defc]]))

(defc not-found
  []
  [:div
   [:h1 "Route not found."]])