# Isomorphic Clojure Webapp

バックエンドもフロントエンドもClojureで書くためのプロジェクトテンプレートです.

## 開発

### 準備

はじめてcloneしたら、下記を実行する:

```sh
lein duct setup
```

これによって、あなたのローカル環境専用の設定が構築されます。

### 開発環境を起動する

まずはREPLを起動しましょう。

```sh
lein repl
```

次に開発環境をロードします。

```clojure
user=> (dev)
:loaded
```

`go` を実行してシステムを初期化します。

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

デフォルトで <http://localhost:3000> にWebサーバーが起動します。

ソースを変更したら、 `reset` を使って、Webサーバーを更新してください。

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

また、ClojureScriptを開発するには、下記を実行します

```sh
npm run dev
```

これによってClojureSctiptのホットリロードが始まります。

### テストの実行

#### バックエンドの単体テスト

REPLから実行しましょう。

```clojure
dev=> (test) ; すべてのテストを探して実行する
dev=> (test 'awesome.app-test) ; 特定のnamespaceのテストを実行する
dev=> (test "test/clj/awesome/app-test.clj") ; 特定のファイルのテストを実行する
dev=> (test 'awesome.app-test/create!-test) ;特定のテスト関数を実行する
dev=> (test ['awesome.app-test/create!-test 'awesome.app-test/update!-test]) ; 複数のテストを実行することもできる
```

#### フロントエンドの単体テスト

専用のビルドプロセスを走らせておきましょう。

```sh
npm run test:dev
open localhost:8021
```

#### 静的解析

ターミナルから実行しましょう。

```sh
lein idiom:{check|fix}
lein format:{check|fix}
```

## テンプレートリポジトリのTODO

- [ ] テストハーネス（with-system）の用意
- [ ] テンプレートから生成した直後の、プロジェクト名変更の方法を書き起こす
- [ ] 継続的開発環境の提供
- [ ] テストのモッキング
- [ ] specの適用
- [ ] BDD
- [ ] CSS in JS
- [ ] フロントエンドの状態管理
- [ ] MySQLなりPostgreSQLなりの、サーバーが必要な開発環境に移行
- [ ] schemaspy対応

## Legal

Copyright © 2022 blackawa
