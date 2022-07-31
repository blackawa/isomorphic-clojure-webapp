(ns isomorphic-clojure-webapp.ui.root
  (:require
    [isomorphic-clojure-webapp.ui.pages.start :as start]
    [reitit.core :as router]
    [reitit.frontend.history :as rfh]
    [rum.core :refer [defc use-effect! use-state]]))


(def ^:private routing
  [["/" {:name  :start
         :title "スタートアップ"
         :view  start/ui}]])


(defc root
  []
  (let [[match set-match!] (use-state nil)
        _                  (use-effect! (fn []
                                          (rfh/start! (router/router routing)
                                                      (fn [match _]
                                                        (set-match! match))
                                                      {:use-fragment false})
                                          #())
                                        [])]
    (if-let [view (-> match :data :view)]
      (view)
      [:p "Loading..."])))
