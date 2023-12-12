<?php
error_reporting(E_ALL);
ini_set('display_errors', 'On');

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;

$app = new Slim\App([
    'settings' => [
        'displayErrorDetails' => true
    ]
]);
$db = new Database\Database();
$conn = $db->connect();
//singUp
$app->post('/User/create', function (Request $request, Response $response) use ($conn) {
    $result = haveEmptyParam(array('user_name', 'email', 'password'), $response);
    if ($result == -1) {
        try {
            $request_data = $request->getParsedBody();
            $name = $request_data['user_name'];
            if (!emailExist($name)) {
                $user_name = $request_data['user_name'];
                $email = $request_data['email'];
                $password = $request_data['password'];
                $hashPass = password_hash($password, PASSWORD_BCRYPT);
                $q = "INSERT INTO `user` ( `user_name`, `email`, `password`) VALUES ( ?, ?, ?)";
                $stmt = $conn->prepare($q);
                $stmt->bindParam(1, $user_name);
                $stmt->bindParam(2, $email);
                $stmt->bindParam(3, $hashPass);
                if ($stmt->execute()) {

                    $message = array();
                    $message['error'] = false;
                    $message['message'] = 'تم تسجيل الحساب بنجاح!';
                    $response->getBody()->write(json_encode($message));

                    return $response->withHeader('Content-Type', ' application/json')
                        ->withStatus(201);
                } else {
                    $message = array();
                    $message['error'] = true;
                    $message['message'] = 'فشل إنشاء الحساب حاول لاحقاً!';
                    $response->getBody()->write(json_encode($message));

                    return $response->withHeader('Content-Type', ' application/json')
                        ->withStatus(403);
                }
            } else {
                $message['error'] = true;
                $message['message'] = 'المستخدم موجود مسبقا حاول مرة اخرى!';
                $response->getBody()->write(json_encode($message));
                return $response->withHeader('Content-Type', ' application/json')
                    ->withStatus(200);
            }
        } catch (Exception $e) {
            $message['error'] = true;
            $message['message'] = $e->getMessage();
            $response->getBody()->write(json_encode($message));
        }
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(401);
    } else {
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(403);
    }
});

//login
$app->post('/User/logIn', function (Request $request, Response $response) use ($conn) {
    if (haveEmptyParam(array('email', 'password'), $response) == -1) {
        try {
            $request_data = $request->getParsedBody();
            $email = $request_data['email'];
            $password = $request_data['password'];
            if (logIn($email, $password)) {
                $q = "Select id,user_name from user  where email = ?";
                $stmt = $conn->prepare($q);
                $stmt->bindParam(1, $email);
                $stmt->execute();
                $user = array();
                $row = $stmt->fetch(PDO::FETCH_ASSOC);
                $user['id'] = $row['id'];
                $user['email'] = $email;
                $user['user_name'] = $row['user_name'];
                $response_data = array();
                $response_data['error'] = false;
                $response_data['message'] = "تم تسجيل الدخول بنجاح";
                $response_data['user'] = $user;
                $response->getBody()->write(json_encode($response_data));

                return $response->withHeader('Content-Type', ' application/json')
                    ->withStatus(200);
            } else {
                $response_data = array();
                $response_data['error'] = true;
                $response_data['message'] = 'فشل الدخول! تحقق من البريد الإلكتروني و كلمة المرور';
                $response->getBody()->write(json_encode($response_data));

                return $response->withHeader('Content-Type', ' application/json')
                    ->withStatus(401);
            }
        } catch (Exception $e) {
            $message['error'] = true;
            $message['message'] = $e->getMessage();
            $response->getBody()->write(json_encode($message));
        }
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(200);
    } else {
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(403);
    }
});

$app->post("/User/update", function (Request $request, Response $response) use ($conn) {
    $request_data = $request->getParsedBody();
    $q = "update user set user_name = ? ,email = ?   where id = ? ";
    $name = $request_data['name'];
    $emil = $request_data['email'];
    $id = $request_data['id'];
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $name);
    $stmt->bindParam(2, $emil);
    $stmt->bindParam(3, $id);
    if ($stmt->execute()) {

        $message = array();
        $message['error'] = false;
        $message['message'] = 'تم التعديل بنجاح!';
        $response->getBody()->write(json_encode($message));

        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(201);
    } else {
        $message = array();
        $message['error'] = true;
        $message['message'] = 'فشل تعديل الحساب حاول لاحقاً!';
        $response->getBody()->write(json_encode($message));

        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(403);
    }
});

$app->get("/{senderId}/{reciverId}/getAllmessages", function (Request $request, Response $response, $params) use ($conn) {
    $id1 = $params['senderId'];
    $id2 = $params['reciverId'];

    $q = "SELECT * FROM `chat` where (senderId = ? and reciverId = ? ) or (senderId = ? and reciverId = ? ) order by id asc";
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $id1);
    $stmt->bindParam(2, $id2);
    $stmt->bindParam(3, $id2);
    $stmt->bindParam(4, $id1);
    $stmt->execute();
    if ($stmt->rowCount() > 0) {
        $messageArr = array();
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $messageItem = array(
                "senderId" => $row['senderId'],
                "reciverId" => $row['reciverId'],
                "message" => $row['message'],
            );
            array_push($messageArr, $messageItem);
        }
        $response_data = array();
        $response_data['error'] = false;
        $response_data['chat'] = $messageArr;
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(200);
    } else {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['chat'] = "no data found!";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(404);
    }
});
$app->post("/send", function (Request $request, Response $response) use ($conn) {
    $request_data = $request->getParsedBody();
    $id1 = $request_data['senderId'];
    $id2 = $request_data['reciverId'];
    $message = $request_data['message'];

    $q = "INSERT INTO `chat` (`senderId`, `reciverId`, `message`) VALUES (? , ?, ?)";
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $id1);
    $stmt->bindParam(2, $id2);
    $stmt->bindParam(3, $message);
    if ($stmt->execute()) {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['message'] = "تم الإرسال";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(201);
    } else {
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = "message sent failed!";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(403);
    }
});
$app->get("/{id}/allUsers", function (Request $request, Response $response, $params) use ($conn) {
    $q = "SELECT * FROM `user` where id <> ?";
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $params['id']);
    $stmt->execute();
    if ($stmt->rowCount() > 0) {
        $userArr = array();
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $userItem = array(
                'id' => $row['id'],
                'email' => $row['email'],
                'user_name' => $row['user_name']
            );
            array_push($userArr, $userItem);
        }
        $response_data = array();
        $response_data['error'] = false;
        $response_data['users'] = $userArr;
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(200);
    } else {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['users'] = "no data found!";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(404);
    }
});
$app->get("/{id}/tasks", function (Request $request, Response $response, $params) use ($conn) {
    $q = "select * from task where user_id = ? ";
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $params['id']);
    $stmt->execute();
    if ($stmt->rowCount() > 0) {
        $taskArr = array();
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $taskItem = array(
                "user_id" => $row['user_id'],
                "task" => $row['task'],
                "content" => $row['content']
            );
            array_push($taskArr, $taskItem);
        }
        $response_data = array();
        $response_data['error'] = false;
        $response_data['Tasks'] = $taskArr;
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(200);
    } else {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['Tasks'] = "لم يتم العثور على بيانات!";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(404);
    }
});
$app->post("/addTask", function (Request $request, Response $response) use ($conn) {
    $q = "INSERT INTO `task` (`user_id`, `task`, `content`) VALUES (?, ?, ?)";
    $request_data = $request->getParsedBody();
    $user_id = $request_data['id'];
    $task = $request_data['task'];
    $content = $request_data['content'];
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $user_id);
    $stmt->bindParam(2, $task);
    $stmt->bindParam(3, $content);
    if ($stmt->execute()) {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['message'] = "تم اضافة تذكير!";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(201);
    } else {
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = "فشل اضافة تذكير حاول لاحقاً!";
        $response->getBody()->write(json_encode($response_data));
        return $response->withHeader('Content-Type', ' application/json')
            ->withStatus(403);
    }
});
//check if email exist
function emailExist($user_name): bool
{
    $q = "Select id from user  where email = ?";
    $db = new Database\Database();
    $conn = $db->connect();
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $user_name);
    $stmt->execute();
    $num = $stmt->rowCount();
    if ($num > 0) {
        return true;
    } else
        return false;
}

//try to login
function logIn($user_name, $password): bool
{
    $q = "Select password from user  where email = ?";
    $db = new Database\Database();
    $conn = $db->connect();
    $stmt = $conn->prepare($q);
    $stmt->bindParam(1, $user_name);
    $stmt->execute();
    $num = $stmt->rowCount();
    if ($num > 0) {
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $pass = $row['password'];
        if (password_verify($password, $pass))
            return true;
        else
            return false;
    } else
        return false;
}

//check request parameters
function haveEmptyParam($required_param, $response): int
{
    $error = -1;
    $error_params = '';


    $request_params = $_REQUEST;

    foreach ($required_param as $param) {
        if (!isset($request_params[$param]) || strlen($request_params[$param]) <= 0) {
            $error = 1;

            $error_params .= $param . ', ';

        }
    }

    if ($error == 1) {
        $error_detail = array();
        $error_detail['error'] = true;
        $error_detail['message'] = 'Required parameters ' . substr($error_params
                , 0, -2) . ' are missing';
        $response->getBody()->write(json_encode($error_detail));
    }
    return $error;
}