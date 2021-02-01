package controller;

import http.HttpRequest;

import java.io.DataOutputStream;

public interface RequestHandler {
    void handleRequest(HttpRequest request, DataOutputStream dos);
}
