<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function(){
                $.ajax({
                    "url": "send/array/one.html",	    // 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": {							// 要发送的请求参数
                        "array": [5,8,12]
                    },
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            });

            $("#btn2").click(function(){
                $.ajax({
                    "url": "send/array/two.html",	    // 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": {							// 要发送的请求参数
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12
                    },
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            });

            $("#btn3").click(function(){
                var array = [5,8,12];
                var requestBody = JSON.stringify(array);
                $.ajax({
                    "url": "send/array/three.html",	    // 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "text",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            });

            $("#btn4").click(function(){
                var student = {
                    "stuId": 5,
                    "stuName": "章回",
                    "address": {
                        "province": "广东",
                        "city": "深圳",
                        "street": "人民公园东侧"
                    },
                    "subjectList": [
                        {
                            "subjectName": "Java编程思想",
                            "subjectScore": 99
                        },
                        {
                            "subjectName": "Java核心技术",
                            "subjectScore": 100
                        }
                    ],
                    "map": {
                        "k1": "v1",
                        "k2": "v2"
                    }

                };
                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/compose/object.json",	    // 请求目标资源的地址
                    "type": "post",						// 请求方式
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",					// 如何对待服务器端返回的数据
                    "success": function(response) {		// 服务器端成功处理请求后调用的回调函数，response是响应体数据
                        console.log(response);
                    },
                    "error":function(response) {		// 服务器端处理请求失败后调用的回调函数，response是响应体数据
                        console.log(response);
                    }
                });
            });

            $("#btn5").click(function(){
                layer.msg("密码错误");
            });


        });

    </script>
</head>
<body>
    <a href="admin/to/login/page.html">测试SSM整合环境</a>
    <br/>
    <a href="test/ssm.html">测试SSM整合环境</a>
    <br/>
    <button id="btn1">Send[5,8,12] One</button>
    <br/><br/>
    <button id="btn2">Send[5,8,12] Two</button>
    <br/><br/>
    <button id="btn3">Send[5,8,12] Three</button>
    <br/><br/>
    <button id="btn4">Send Student</button>
    <br/><br/>
    <button id="btn5">layer弹窗</button>
    <br/><br/>

</body>
</html>
