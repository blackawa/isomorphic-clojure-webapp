(ns duct.router.reitit
  (:require [clojure.walk :as walk]
            [duct.core.resource]
            [integrant.core :as ig]
            [reitit.coercion.spec :as coercion.spec]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as ring.coercion]))


(def default-route-opts
  {:coercion coercion.spec/coercion
   :middleware [ring.coercion/coerce-exceptions-middleware
                ring.coercion/coerce-request-middleware
                ring.coercion/coerce-response-middleware]})


(def default-default-handlers
  {:not-found          (ig/ref :duct.handler.static/not-found)
   :not-acceptable     (ig/ref :duct.handler.static/bad-request)
   :method-not-allowed (ig/ref :duct.handler.static/method-not-allowed)})


(defn- resolve-symbol
  [x]
  (if-let [var (and (symbol? x) (resolve x))]
    (var-get var)
    x))


(defmethod ig/prep-key :duct.router/reitit [_ {:keys [routes]
                                               ::ring/keys [opts default-handlers]}]
  {:routes (walk/postwalk resolve-symbol routes)
   ::ring/opts (merge {:data default-route-opts} opts)
   ::ring/default-handlers (merge default-default-handlers default-handlers)})


(defmethod ig/init-key :duct.router/reitit
  [_ {:keys [routes] ::ring/keys [opts default-handlers]}]
  (ring/ring-handler
    (ring/router routes opts)
    (ring/routes
      (ring/create-resource-handler {:path "/"})
      (ring/create-default-handler default-handlers))))
