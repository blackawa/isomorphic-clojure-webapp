(ns isomorphic-clojure-webapp.handler.app
  (:require
    [integrant.core :as ig]
    [rum.core :refer [render-html]]))


(defmethod ig/init-key ::index [_ _]
  (fn [req]
    (prn req)
    {:status 200
     :headers {"content-type" "text/html"}
     :body (render-html [:html
                         [:body
                          [:#app]
                          [:script {:src "/js/main.js"}]]])}))
