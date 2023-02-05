FROM clojure:temurin-19-lein-bullseye AS builder
WORKDIR /usr/app/src
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash -
RUN apt-get install -y nodejs
COPY package*.json ./
COPY project.clj ./
RUN npm install
RUN lein deps
COPY ./ ./
RUN npm run package
RUN lein do clean, uberjar

FROM 19-jre-focal AS runner
WORKDIR /
COPY --from=builder /usr/app/src/target/app.jar /app.jar
ENV TZ="Asia/Tokyo"
ENTRYPOINT java -Duser.language=ja -Duser.country=JP -jar app.jar