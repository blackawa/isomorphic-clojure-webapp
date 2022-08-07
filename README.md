# Isomorphic Clojure Webapp

バックエンドもフロントエンドもClojureで書くためのプロジェクトテンプレートです.

:warning::warning::warning: Windowsユーザーは必ずWSL2のファイルシステム上にクローンしてください。ホットリロードが効かず、開発体験を著しく損ねます。WSL2について詳しくは[こちら](https://docs.microsoft.com/ja-jp/windows/wsl/setup/environment#file-storage) :warning::warning::warning:

## 開発

### 準備（VSCode+Calvaの場合）

このリポジトリの推奨開発環境は、VSCode+devcontainer+Calvaです。
コードベースを開くとdevcontainerを利用するか確認されるので、承認してください。
見逃した時は、コマンドパレットから `Remote-Container: Reopen in container` を選択してください。

初回のみ、

- Dockerイメージのビルドプロセス
- 推奨プラグインのインストールの要否

を確認されます。どちらも受け入れてください。

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

また、ClojureScriptでアプリケーションを開発するには、下記を実行します

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

`npm run dev` を開始していれば、下記にアクセスすることで自動的に単体テストが実行されます。

```sh
open localhost:8021
```

#### 静的解析

ターミナルから実行しましょう。

```sh
lein idiom:{check|fix}
lein format:{check|fix}
```

#### 結合テスト

ターミナルから実行しましょう。

```sh
npm run test:integration
```

### マイグレーションの追加

`resources/config.edn` の `:duct.profile/base` 内に下記を追加しましょう。

```clj
:duct.migrator/ragtime {:migrations [#ig/ref your.first.migration]}
[:duct.migrator.ragtime/sql :your.first.migration] {:up "create table tasks (id integer autoincrement primary key, label varcahr(128) not null);"
                                                    :down "drop table tasks;"}
```

その後、開発サーバーを `(reset)` しましょう。マイグレーションが実行されます。

ductのマイグレーターには下記のような機能もあります。詳しくは [duct-framework/migrator.ragtime](https://github.com/duct-framework/migrator.ragtime) を読んでください。

- SQLファイルを利用する
- 本番環境でマイグレーターのみを実行する

## テンプレートリポジトリのTODO

- [ ] リモートコンテナの提供
    - [ ] GitLensとdifftasticを使った差分表示
    - [ ] NodeとJavaが使える環境
- [ ] テストのモッキング例
- [ ] specの適用例
- [ ] schemaspyによるER図の自動生成とチーム共有
- [ ] カバレッジレポートの開発者への共有
- [ ] Dockerイメージ作成（マイグレーションも実行する）

## Legal

Copyright © 2022 blackawa
