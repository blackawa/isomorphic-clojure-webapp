(ns duct.router.reitit
  (:require [clojure.walk :as walk]
            [duct.core :as duct]
            [duct.core.resource]
            [integrant.core :as ig]
            [reitit.coercion.spec :as coercion.spec]
            [reitit.coercion.schema :as coercion.schema]
            [reitit.coercion.malli :as coercion.malli]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as ring.coercion]))


(def default-default-handlers
  {:not-found          (ig/ref :duct.handler.static/not-found)
   :not-acceptable     (ig/ref :duct.handler.static/bad-request)
   :method-not-allowed (ig/ref :duct.handler.static/method-not-allowed)})


(defn- resolve-symbol
  [x]
  (if-let [var (and (symbol? x) (resolve x))]
    (var-get var)
    x))


(defmethod ig/init-key :duct.router.reitit.coercion/spec [_ _]
  coercion.spec/coercion)


(defmethod ig/init-key :duct.router.reitit.coercion/schema [_ _]
  coercion.schema/coercion)


(defmethod ig/init-key :duct.router.reitit.coercion/malli [_ _]
  coercion.malli/coercion)


(defmethod ig/init-key ::coerce-exceptions-middleware [_ _]
  ring.coercion/coerce-exceptions-middleware)


(defmethod ig/init-key ::coerce-request-middleware [_ _]
  ring.coercion/coerce-request-middleware)


(defmethod ig/init-key ::coerce-response-middleware [_ _]
  ring.coercion/coerce-response-middleware)


(defmethod ig/prep-key :duct.router/reitit [_ {:keys [routes]
                                               ::ring/keys [opts default-handlers]}]
  {:routes (walk/postwalk resolve-symbol routes)
   ::ring/opts opts
   ::ring/default-handlers default-handlers})


(defmethod ig/init-key :duct.router/reitit
  [_ {:keys [routes] ::ring/keys [opts default-handlers]}]
  (prn "init!:" routes)
  (ring/ring-handler
   (ring/router routes opts)
   (ring/routes
    (ring/create-resource-handler {:path "/"})
    (ring/create-default-handler default-handlers))))


(defmethod ig/init-key :duct.module/reitit [_ routes]
  (fn [config]
    (duct/merge-configs
     config
     {:duct.router/reitit {:routes routes
                           ::ring/opts {:data {:middleware [(ig/ref ::coerce-exceptions-middleware)
                                                            (ig/ref ::coerce-request-middleware)
                                                            (ig/ref ::coerce-response-middleware)]
                                               :coercion (ig/ref :duct.router.reitit/coercion)}}
                           ::ring/default-handlers default-default-handlers}})))
