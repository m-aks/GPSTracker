package com.example.gpstracker.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class AuthRequest extends StringRequest {

    private final Map<String, String> headers;

    public AuthRequest(String url, Map<String, String> headers, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }
}