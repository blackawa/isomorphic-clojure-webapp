(defproject isomorphic-clojure-webapp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[com.github.seancorfield/next.jdbc "1.2.780"]
                 [duct/core "0.8.0"]
                 [duct/module.logging "0.5.0"]
                 [duct/module.sql "0.6.1"]
                 [duct/module.web "0.7.3"]
                 [metosin/malli "0.11.0"]
                 [metosin/reitit "0.5.18"]
                 [metosin/reitit-swagger "0.6.0"]
                 [metosin/reitit-swagger-ui "0.6.0"]
                 [org.clojure/clojure "1.10.3"]
                 [org.postgresql/postgresql "42.5.3"]
                 [rum "0.12.9"]]
  :plugins [[duct/lein-duct "0.12.3"]
            [lein-cloverage "1.2.2"]
            [mvxcvi/cljstyle "0.15.0"]
            [jonase/eastwood "1.2.4"]]
  :uberjar-name "app.jar"
  :main ^:skip-aot isomorphic-clojure-webapp.main
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :dependencies [[cider/piggieback "0.5.2"]]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/clj/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[clj-kondo "RELEASE"]
                                   [eftest "0.5.9"]
                                   [hawk "0.2.11"]
                                   [integrant/repl "0.3.2"]
                                   [kerodon "0.9.1"]
                                   [mvxcvi/cljstyle "0.15.0" :exclusions [org.clojure/clojure]]]
                  :aliases {"idiom:check" ["do"
                                           ["eastwood"]
                                           ["run" "-m" "clj-kondo.main" "--lint" "src" "test"]]
                            "idiom:fix" ["do"
                                         ["run" "-m" "clj-kondo.main" "--lint" "src" "test"]]
                            "format:check" ["run" "-m" "cljstyle.main" "check" "src" "test"]
                            "format:fix" ["run" "-m" "cljstyle.main" "fix" "src" "test"]
                            "test" ["cloverage"]}}})
