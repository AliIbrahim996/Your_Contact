<?php


use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\App;

require '../vendor/autoload.php';
require '../src/config/Database.php';
$app = new App;

$app->get('/hello/{name}', function (Request $request, Response $response) {
    $name = $request->getAttribute('name');
    $response->getBody()->write("Hello, $name");
    return $response;
});

require_once "../src/routes/dbOperation.php";

try {
    $app->run();
} catch (Throwable $e) {
    echo $e->getMessage();
}
