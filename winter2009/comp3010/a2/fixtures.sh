#!/bin/sh

curl -XPOST -d'{"title":"3010 Assignment","description":"Web Assignment is Due","date":"1226834000000","user":"burke"}' http://localhost:15021/events
curl -XPOST -d'{"title":"2150 Assignment","description":"Crazy big assignment is due","date":"1236334000000","user":"burke"}' http://localhost:15021/events
curl -XPOST -d'{"title":"3620 Term Paper","description":"Net Neutrality Term Paper due","date":"1236834000000","user":"burke"}' http://localhost:15021/events

curl -XPOST -d'{"title":"Save the World","description":"Why cant the world just start saving itself?","date":"1236834000000","user":"superman"}' http://localhost:15021/events

curl -XPOST -d'{"name":"burke"}' http://localhost:15021/users
curl -XPOST -d'{"name":"superman"}' http://localhost:15021/users

