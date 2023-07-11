(ns isomorphic-clojure-webapp.storybook.first
  (:require [isomorphic-clojure-webapp.ui.pages.start :as c]))


(defn default-story
  []
  (c/ui))


(def ^:export story
  {:title "Button"
   :component nil
   :stories (js->clj [{:name "Default"
                       :render default-story}])})
