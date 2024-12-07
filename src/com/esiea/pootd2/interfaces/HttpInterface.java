package com.esiea.pootd2.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.esiea.pootd2.controllers.IExplorerController;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpInterface extends AbstractInterface implements HttpHandler {
    private HttpServer server;
    private ExecutorService threadPool;
    private boolean serverShouldClose;

    public HttpInterface(IExplorerController controller) {
        super(controller);

        serverShouldClose = false;

        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/", this);
            threadPool = Executors.newFixedThreadPool(1);
            server.setExecutor(threadPool);
        } catch (IOException e) {
            System.err.println("Failed to initialize HttpInterface");
            e.printStackTrace();
            server = null;
        }
    }

    public void run() {
        assert server != null;
        server.start();
        System.out.println("Server listening on http://localhost:8001");
        try {
            while (!serverShouldClose)
                Thread.sleep(200);
            System.out.println("Server closed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.stop(1);
        threadPool.shutdownNow();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            URI uri = exchange.getRequestURI();
            if ("/execute".equals(uri.getPath())) {
                handlePostExecute(exchange);
                return;
            }
        }

        if ("GET".equals(exchange.getRequestMethod())) {
            handleGetDefault(exchange);
            return;
        }

        handleError(exchange);
    }

    private void handleGetDefault(HttpExchange exchange) throws IOException {
        OutputStream stream = exchange.getResponseBody();

        String htmlResponse = constructWebPage();

        exchange.sendResponseHeaders(200, htmlResponse.length());
        stream.write(htmlResponse.getBytes());
        stream.flush();
        stream.close();
    }

    private void handlePostExecute(HttpExchange exchange) throws IOException {
        
        //to uncomment
        //assert controller != null;
        
        InputStream inStream = exchange.getRequestBody();
        OutputStream outStream = exchange.getResponseBody();

        String command = new String(inStream.readAllBytes(), StandardCharsets.UTF_8);

        String response = "";
        if ("exit".equals(command)) {
            System.out.println("Closing server...");
            response = "Server closed";
            serverShouldClose = true;
        } 
        //to uncomment
        //else
        //     response = controller.executeCommand(command);

        exchange.sendResponseHeaders(200, response.length());
        outStream.write(response.getBytes());
        outStream.flush();
        outStream.close();
    }

    private void handleError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(400, 0);
        exchange.close();
    }

    private String constructWebPage() {
        return """
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>File explorer</title>

                    <style>
                        * {
                            font-family: 'Courier New', Courier, monospace;
                        }

                        html,
                        body {
                            width: 100%;
                            height: 100%;
                            padding: 0;
                            margin: 0;
                        }

                        .console {
                            width: calc(100vw - 10px);
                            min-height: calc(100vh - 10px);
                            background: #1c1c1c;
                            color: white;
                            padding: 5px;
                            font-size: 16px;
                            font-weight: bold;
                        }

                        #console-user-input {
                            background: none;
                            outline: none;
                            border: none;
                            color: inherit;
                            font-size: inherit;
                            font-weight: inherit;
                            margin: none;
                            padding: none;
                            width: calc(100% - 25px);
                        }
                    </style>
                </head>

                <body>
                    <div class="console">
                        <div id="console-content"></div>
                        <div id="console-input">> <input type="text" id="console-user-input" /></div>
                    </div>
                </body>

                <script text="javascript">
                    var consoleContent = document.getElementById("console-content");
                    var consoleInput = document.getElementById("console-input");
                    var userInput = document.getElementById("console-user-input");

                    function request(method, url, headers, data) {
                        return new Promise((resolve, reject) => {
                            let xhttp = new XMLHttpRequest();
                            xhttp.onreadystatechange = () => {
                                if (xhttp.readyState == 4) {
                                    if (xhttp.status >= 200 && xhttp.status < 300)
                                        resolve({ status: xhttp.status, body: xhttp.responseText });
                                    else
                                        reject({ status: xhttp.status, body: xhttp.responseText });
                                }
                            };
                            xhttp.onabort = xhttp.onerror = () => {
                                reject({ status: 0, body: null });
                            };
                            xhttp.open(method, url, true);
                            for (let key of Object.keys(headers))
                                xhttp.setRequestHeader(key, headers[key]);
                            xhttp.send(data);
                        });
                    }

                    document.addEventListener("keydown", (e) => {
                        if (e.key != "Enter")
                            return;
                        let command = userInput.value;
                        consoleContent.innerText += `> ${userInput.value}\n`;
                        consoleInput.style.display = "none";
                        userInput.value = "";
                        request("POST", `http://${window.location.hostname}:8001/execute`, {}, command).then(res => {
                            if (res.body.length > 0)
                                consoleContent.innerText += `${res.body}\n`
                            consoleInput.style.display = "block";
                            userInput.focus();
                        }).catch(err => {
                            consoleInput.style.display = "block";
                            userInput.focus();
                        });
                    })
                </script>
                    """;
    }
}