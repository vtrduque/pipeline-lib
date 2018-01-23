#!/usr/bin/groovy

package io.vtrduque;

def helloWorld() {
  println 'Hello world :)'
}

def npmInstall() {
  println 'Running npm install'
  sh 'npm install'
}

def dockerBuildImage(image) {
  sh "docker build -t ${imageName}  ."
}

def dockerPush(registry, credentials, imageName, prefix, version) {
  docker.withRegistry(registry, credentials) {
    fullName = "$prefix-$version"
    docker.image(imageName).push(fullName)
  }
}

def isEnabled() {
  config = readJSON file: 'config.json'

  println "pipeline config ==> ${config}"

  if (!config["pipeline"]["enabled"]) {
    println "pipeline disabled"
    sh "exit 0"
  }
}
