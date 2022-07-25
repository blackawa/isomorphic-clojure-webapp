(ns isomorphic-clojure-webapp.app
  (:require
    [isomorphic-clojure-webapp.ui.root :refer [root]]
    [rum.core :refer [mount]]))


;; shadow-cljs.ednから直接呼ばれるため.
#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}


(defn init
  []
  (js/console.log "Hello from ClojureScript")
  (mount (root) (js/document.getElementById "app")))
